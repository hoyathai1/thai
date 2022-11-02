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

    @RequestMapping(value = "/warning", method = RequestMethod.GET)
    public String checkAjax(HttpServletRequest request, HttpServletResponse response, Model model) {


        return "/common/warning";
    }

}
