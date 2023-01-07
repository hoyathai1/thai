package com.travel.thai.common.controller;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.*;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.domain.NoticeDto;
import com.travel.thai.common.service.NoticeService;
import com.travel.thai.common.util.LogUtil;
import com.travel.thai.common.util.StringUtils;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.service.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommonController {
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
    private BoardCategoryService boardCategoryService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "redirect:/board/list?type=all&best=&category=thai&pageNum=0";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {
        String uri = request.getHeader("Referer");

        if (StringUtils.isNotEmpty(uri)) {
            if (!uri.contains("login") && !uri.contains("logout") && !uri.contains("signUp") && !uri.contains("menu")) {
                request.getSession().setAttribute("mainPage", uri);
            }
        }

        return "/common/menu";
    }

    @RequestMapping(value = "/menu/back", method = RequestMethod.GET)
    public String back(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {

        return "/common/menu";
    }

    @RequestMapping(value = "/close", method = RequestMethod.GET)
    public String close(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {
        String uri = (String) request.getSession().getAttribute("mainPage");

        if (StringUtils.isEmpty(uri)) {
            return "redirect:/board/list?type=all&best=&category=thai&pageNum=0";
        }

        request.getSession().setAttribute("mainPage", "");

        return "redirect:" + uri;
    }

    @RequestMapping(value = "/menu/account", method = RequestMethod.GET)
    public String account(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {

        if (user == null) {
            return "/common/warning";
        }


        user.setPassword(null);
        model.addAttribute("user", user);

        model.addAttribute("email1", getEmail1(user.getEmail()));
        model.addAttribute("email2", getEmail2(user.getEmail()));

        return "/common/account";
    }

    @RequestMapping(value = "/menu/notice", method = RequestMethod.GET)
    public String notice(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {

        if (user == null) {
            return "/common/warning";
        }

        List<NoticeDto> list = noticeService.search();

        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("list", list);

        return "/common/notice";
    }


    @RequestMapping(value = "/menu/myList", method = RequestMethod.GET)
    public String myList(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search
            , @AuthenticationPrincipal User user) {

        if (user == null) {
            return "/common/warning";
        }

        search.setUserId(user.getUserId());
        Page<BoardDto> list = userService.searchBoardById(search);

        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/common/myList";
    }

    @RequestMapping(value = "/menu/myList/detail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> listAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            BoardDto result = boardService.searchBoardContents(search);
            map.put("contents", result.getContents());

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/menu/myNoti", method = RequestMethod.GET)
    public String myNoti(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search
            , @AuthenticationPrincipal User user) {

        if (user == null) {
            return "/common/warning";
        }

        search.setUserId(user.getUserId());
        Page<BoardNotiDto> list = boardNotiService.search(search);

        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/common/myNoti";
    }

    @RequestMapping(value = "/menu/myComment", method = RequestMethod.GET)
    public String myComment(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search
            , @AuthenticationPrincipal User user) {

        if (user == null) {
            return "/common/warning";
        }

        search.setUserId(user.getUserId());
        Page<CommentDto> list= commentService.searchListForPc(search);
        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/common/myComment";
    }

    @RequestMapping(value = "/menu/bookmark", method = RequestMethod.GET)
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

        return "/common/bookmark";
    }

    @RequestMapping(value = "/warning", method = RequestMethod.GET)
    public String checkAjax(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "/common/warning";
    }

    @RequestMapping(value = "/category/type", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> typeAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            List<BoardType> result = boardCategoryService.getBoardTypeList(search.getCategory());
            map.put("list", result);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> categoryListAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;
        boolean result = false;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("list", boardCategoryService.getBoardCategoryAllList());

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
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
