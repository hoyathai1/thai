package com.travel.thai.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board/*")
public class BbsController {
    @GetMapping("/1")
    public String temp(HttpServletRequest request, HttpServletResponse response) {
        return "board/1";
    }

}
