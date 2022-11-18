package com.travel.thai.common.controller;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.BoardNotiService;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.service.NoticeService;
import com.travel.thai.common.util.LogUtil;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "redirect:/board/list?type=all&best=&category=thai&pageNum=0";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {
        String uri = request.getHeader("Referer");
//        if (uri != null && !uri.contains("/login")) {
//            request.getSession().setAttribute("prevPage", uri);
//        }
        model.addAttribute("prevPage", uri);

        return "/common/menu";
    }

    @RequestMapping(value = "/menu/account", method = RequestMethod.GET)
    public String account(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {

        if (user == null) {
            return "/common/warning";
        }

        user.setPassword(null);
        model.addAttribute("user", user);

        return "/common/account";
    }

    @RequestMapping(value = "/menu/notice", method = RequestMethod.GET)
    public String notice(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {

        if (user == null) {
            return "/common/warning";
        }

        List<Notice> list = noticeService.search();

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


    @RequestMapping(value = "/warning", method = RequestMethod.GET)
    public String checkAjax(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "/common/warning";
    }

}
