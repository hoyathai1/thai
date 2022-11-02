package com.travel.thai.bbs.controller;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.bbs.service.CommentService;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("search") Search search) {
        return "board/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> listAjax(@RequestBody Search search) {
        System.out.println("" + search.getPageNum() +"|"+ search.getPageSize());
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            Page<BoardDto> list = boardService.searchBoard(search);
            map.put("list", list);
            map.put("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
            map.put("search", search);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("search") Search search, Model model) {
        Board board = boardService.searchOne(search);
        boardService.increseViewCount(search);

        model.addAttribute("board", board);


        return "board/view";
    }

    @RequestMapping(value = "/save/comment", method = RequestMethod.POST)
    public ResponseEntity<String> saveComment(HttpServletRequest request, HttpServletResponse response
            , @RequestBody CommentDto commentDto)  {
        ResponseEntity<String> entity = null;

        try {
            commentDto.setIp(request.getRemoteAddr());
            Comment result = commentService.saveComment(commentDto);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/search/comment", method = RequestMethod.POST)
    public ResponseEntity<String> searchComment(HttpServletRequest request, HttpServletResponse response
            ,@RequestBody Search search)  {
        ResponseEntity<String> entity = null;

        try {
            Map<String, Object> map = new HashMap<>();
            CommentDto result = commentService.search(search);

            map.put("list", result.getList());
            map.put("totalCount", result.getCommentTotalCount());
            map.put("pageDto", new PageDto(result.getList().getTotalElements(), result.getList().getPageable()));
            map.put("search", search);

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/delete/comment", method = RequestMethod.POST)
    public ResponseEntity<String> deleteComment(HttpServletRequest request, HttpServletResponse response
            ,@RequestBody Search search)  {
        ResponseEntity<String> entity = null;

        try {
            boolean result = commentService.deleteComment(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("search") Search search) {
        return "board/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerAjax(HttpServletRequest request, HttpServletResponse response
                                                , @RequestBody Board board) {
        ResponseEntity<String> entity = null;

        try {
            board.setIp(request.getRemoteAddr());
            Board result = boardService.saveBoard(board);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modify(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("search") Search search, Model model) {
        try {
            if (StringUtils.isEmpty(search.getPassword())) {
                return "redirect:/warning";
            }
            String encPassword = RSAUtil.decryptRSA(search.getPassword());
            search.setPassword(encPassword);
            boolean result = boardService.isCheckPassword(search);

            if (!result) {
                return "redirect:/warning";
            }

            Board board = boardService.searchOne(search);
            model.addAttribute("board", board);
            model.addAttribute("search", search);
        } catch (Exception e) {
            return "redirect:/warning";
        }

        return "board/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modifyAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Board board) {
        ResponseEntity<String> entity = null;

        try {
            boolean result = boardService.modifyBoard(board);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    /**
     * 게시물 비밀번호 확인
     *
     * @param request
     * @param response
     * @param search
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> checkAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Search search) {
        ResponseEntity<Map<String, Object>> entity = null;

        try {
            Map<String, Object> map = new HashMap<>();

            if ("modify".equals(search.getType())) {
                boolean result = boardService.isCheckPassword(search);

                if (result) {
                    // 비밀번호 일치
                    String encPassword = RSAUtil.encryptRSA(search.getPassword());
                    String url = "/board/modify?boardNum=" + search.getBoardNum() + "&password=" + encPassword;
                    map.put("redirect", url);
                }

                map.put("result", result);
            } else if ("delete".equals(search.getType())) {
                boolean result = boardService.isCheckPassword(search);

                if (result) {
                    boardService.deleteBoard(search);
                }

                String url = "/board/list";
                map.put("redirect", url);
                map.put("result", result);
            }

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
}
