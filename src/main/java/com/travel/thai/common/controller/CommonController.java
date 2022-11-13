package com.travel.thai.common.controller;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Search;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CommonController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "redirect:/board/list?type=all&best=&category=thai&pageNum=0";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(HttpServletRequest request, HttpServletResponse response, Model model) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }



        return "/common/menu";
    }

    @RequestMapping(value = "/warning", method = RequestMethod.GET)
    public String checkAjax(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "/common/warning";
    }

}
