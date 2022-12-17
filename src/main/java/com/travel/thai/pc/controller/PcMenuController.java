package com.travel.thai.pc.controller;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.*;
import com.travel.thai.common.service.NoticeService;
import com.travel.thai.common.util.LogUtil;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.common.util.StringUtils;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
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

@Controller
@RequestMapping("/pc/menu/*")
@Slf4j
public class PcMenuController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardNotiService boardNotiService;

    @Autowired
    private BookMarkService bookMarkService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardCategoryService categoryService;

    @Autowired
    private BoardCategoryService boardCategoryService;

    @Autowired
    private LikesService likesService;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(HttpServletRequest request, HttpServletResponse response, Model model
            , @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));
        model.addAttribute("allCategory", boardCategoryService.getBoardCategoryList());

        if (user == null) {
            return "/common/warning";
        }

        user.setPassword(null);
        model.addAttribute("user", user);

        model.addAttribute("email1", getEmail1(user.getEmail()));
        model.addAttribute("email2", getEmail2(user.getEmail()));
        model.addAttribute("search", search);

        return "/pc/menu/account";
    }

    @RequestMapping(value = "/accountPwd", method = RequestMethod.GET)
    public String accountPwd(HttpServletRequest request, HttpServletResponse response, Model model
            , @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));
        model.addAttribute("allCategory", boardCategoryService.getBoardCategoryList());

        if (user == null) {
            return "/common/warning";
        }

        model.addAttribute("search", search);

        return "/pc/menu/accountPwd";
    }

    @RequestMapping(value = "/myList", method = RequestMethod.GET)
    public String myList(HttpServletRequest request, HttpServletResponse response, Model model
            , @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));
        model.addAttribute("allCategory", boardCategoryService.getBoardCategoryList());

        if (user == null) {
            return "/common/warning";
        }

        search.setUserId(user.getUserId());
        Page<BoardDto> list = boardService.searchByIdForPc(search);

        list.stream().forEach(x->{
            x.setTypeName(categoryService.getBoardTypeName(x.getType(), x.getCategory()));
        });

        model.addAttribute("list", list);
        model.addAttribute("search", search);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));

        return "/pc/menu/myList";
    }

    @RequestMapping(value = "/myView", method = RequestMethod.GET)
    public String view(HttpServletRequest request, @ModelAttribute("search") Search search, Model model
            , @AuthenticationPrincipal User user) {
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));
        model.addAttribute("allCategory", boardCategoryService.getBoardCategoryList());

        if (user == null) {
            return "/common/warning";
        }

        BoardDto board = boardService.searchOne(search);
        int likes = likesService.getLikesCount(search.getBoardNum());

        model.addAttribute("likes", likes);
        model.addAttribute("board", board);
        model.addAttribute("search", search);

        return "pc/menu/myView";
    }

    @RequestMapping(value = "/myModify", method = RequestMethod.GET)
    public String modify(HttpServletRequest request, @ModelAttribute("search") Search search, Model model
            , @AuthenticationPrincipal User user) {
        model.addAttribute("boardCategory", boardCategoryService.getBoardCategory(search.getCategory()));
        model.addAttribute("boardType", boardCategoryService.getBoardTypeList(search.getCategory()));
        model.addAttribute("allCategory", boardCategoryService.getBoardCategoryList());

        if (user == null) {
            return "/common/warning";
        }

        try {
            HttpSession session = request.getSession();
            String[] fileList = (String[]) session.getAttribute("fileNameList");

            if (fileList!= null && fileList.length > 0) {
                session.removeAttribute("fileNameList");
            }

            BoardDto board = boardService.searchOne(search);

            if (user.getUserId().equals(board.getUserId())) {
                board.setTypeName(boardCategoryService.getBoardTypeName(board.getType(),board.getCategory()));
                model.addAttribute("board", board);
                model.addAttribute("search", search);
            } else {
                return "redirect:/warning";
            }
        } catch (Exception e) {
            return "redirect:/warning";
        }

        return "pc/menu/myModify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modifyAjax(HttpServletRequest request, HttpServletResponse response
            , @RequestBody Board board, @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity = null;
        HttpSession session = request.getSession();

        try {
            boolean result = boardService.modifyBoard(board, user);

            String[] fileNameList = (String[]) session.getAttribute("fileNameList");
            boardService.moveImage(fileNameList, board);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }

    @RequestMapping(value = "/myBookmark", method = RequestMethod.GET)
    public String bookmark(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search
            , @AuthenticationPrincipal User user) {

        if (user == null) {
            return "redirect:/warning";
        }

        search.setUserId(user.getUserId());
        Page<BookMarkDto> list = bookMarkService.searchBoard(search);

        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/pc/menu/myBookmark";
    }

    private String getEmail1(String email) {
        if (StringUtils.isEmpty(email) || "@".equals(email)) {
            return "";
        }

        return email.substring(0, email.indexOf("@"));
    }

    private String getEmail2(String email) {
        if (StringUtils.isEmpty(email) || "@".equals(email)) {
            return "";
        }

        return email.substring(email.indexOf("@")+1);
    }
}
