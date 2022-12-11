package com.travel.thai.admin.board.board;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.PageDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.service.BoardCategoryService;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/board/board")
public class AdminBoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardCategoryService categoryService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        HttpSession session = request.getSession();
        String[] fileList = (String[]) session.getAttribute("fileNameList");

        if (fileList!= null && fileList.length > 0) {
            session.removeAttribute("fileNameList");
        }

        Page<BoardDto> list = boardService.searchBoardForAdmin(search);

        list.stream().forEach(x->{
            x.setTypeName(categoryService.getBoardTypeName(x.getType(), x.getCategory()));
        });

        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/admin/board/board/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {

            boardService.deleteBoard(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public ResponseEntity<String> restoreAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {

            boardService.restoreBoard(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> listAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            BoardDto result = boardService.searchBoardDetailForAdmin(search);
            map.put("contents", result.getContents());
            map.put("title", result.getTitle());

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> listAjax(HttpServletRequest request, @RequestBody Board board
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        HttpSession session = request.getSession();
        try {
            boardService.modifyBoardForAdmin(board);

            String[] fileNameList = (String[]) session.getAttribute("fileNameList");
            boardService.moveImage(fileNameList, board);

            entity = new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }
}
