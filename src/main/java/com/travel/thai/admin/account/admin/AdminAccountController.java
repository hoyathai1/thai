package com.travel.thai.admin.account.admin;

import com.travel.thai.bbs.domain.PageDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.service.EmailService;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.domain.UserDto;
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

@Controller
@RequestMapping("/admin/account/admin")
public class AdminAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {

        search.setRole("ROLE_ADMIN");
        Page<UserDto> list = userService.search(search);
        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/admin/account/admin/list";
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseEntity<String> listAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            userService.deleteUser(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/restoreUser", method = RequestMethod.POST)
    public ResponseEntity<String> restoreUserAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            userService.restoreUser(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/modifyEmail", method = RequestMethod.POST)
    public ResponseEntity<String> modifyEmailAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            userService.modifyEmailForAdmin(search.getUserId(), search.getEmail());

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerAjax(HttpServletRequest request, @RequestBody UserDto userDto
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            userDto.setIp(request.getRemoteAddr());
            userDto.setAuth("ROLE_ADMIN");
            userService.signUp(userDto);

            result = true;

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    public ResponseEntity<String> modifyPwdAjax(HttpServletRequest request, @RequestBody UserDto userDto
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            userService.modfiyPasswordForAdmin(userDto.getUserId(), userDto.getPassword());

            result = true;

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
}
