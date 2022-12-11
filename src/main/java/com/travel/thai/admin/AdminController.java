package com.travel.thai.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping(value = {"/main"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "/admin/main";
    }
}
