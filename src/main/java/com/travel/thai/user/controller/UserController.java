package com.travel.thai.user.controller;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.service.EmailService;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.domain.UserDto;
import com.travel.thai.user.service.UserDetailService;
import com.travel.thai.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value = "error", required = false)String error,
                       @RequestParam(value = "errorType", required = false)String errorType,
                       Model model) {

        model.addAttribute("error", error);
        model.addAttribute("errorType", errorType);

        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "user/login";
    }

    @RequestMapping(value = {"/pc/login"}, method = RequestMethod.GET)
    public String pcList(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value = "error", required = false)String error,
                       @RequestParam(value = "errorType", required = false)String errorType,
                       Model model) {

        model.addAttribute("error", error);
        model.addAttribute("errorType", errorType);

        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "pc/user/login";
    }

    @RequestMapping(value = {"/signUp"}, method = RequestMethod.GET)
    public String signUp(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {

        if (user != null) {
            return "redirect:/board/list?type=all&best=&category=thai&pageNum=0";
        }

        return "user/signUp";
    }

    @RequestMapping(value = {"/pc/signUp"}, method = RequestMethod.GET)
    public String pcSignUp(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {

        if (user != null) {
            return "redirect:/board/list?type=all&best=&category=thai&pageNum=0";
        }

        return "pc/user/signUp";
    }

    @RequestMapping(value = {"/signUp"}, method = RequestMethod.POST)
    public ResponseEntity<String> signUpPOST(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto userDto) {
        ResponseEntity<String> entity = null;

        try {
            userDto.setIp(request.getRemoteAddr());
            userService.signUp(userDto);

            /**
             * 회원가입후 자동로그인
             */
            UserDetails ckUserDetails = userDetailService.loadUserByUsername(userDto.getUserId());
            Authentication authentication = new UsernamePasswordAuthenticationToken(ckUserDetails, userDto.getPassword(), ckUserDetails.getAuthorities());

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            entity = new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/checkUserId"}, method = RequestMethod.POST)
    public ResponseEntity<String> checkUserId(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto userDto) {
        ResponseEntity<String> entity = null;

        try {
            boolean resutl = userService.isExistUserId(userDto.getUserId());
            entity = new ResponseEntity(resutl, HttpStatus.OK);
        } catch (Exception e) {
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/checkUserName"}, method = RequestMethod.POST)
    public ResponseEntity<String> checkUserName(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDto userDto) {
        ResponseEntity<String> entity = null;

        try {
            boolean resutl = userService.isExistUserName(userDto.getName());
            entity = new ResponseEntity(resutl, HttpStatus.OK);
        } catch (Exception e) {
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/find/pwd"}, method = RequestMethod.GET)
    public String findPwd(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "user/findPwd";
    }

    @RequestMapping(value = {"/tempPwdEmail"}, method = RequestMethod.POST)
    public ResponseEntity<String> sendEmail(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody UserDto userDto) {
        ResponseEntity<String> entity = null;
        boolean result = false;

        try {
            emailService.createTempPwdAndSendEmail(userDto.getEmail(), userDto.getUserId());

            entity = new ResponseEntity("true", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/user/modifyName"}, method = RequestMethod.POST)
    public ResponseEntity<String> modifyName(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody UserDto userDto, @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity = null;
        boolean result = false;

        try {
            if (user == null) {
                entity = new ResponseEntity(false, HttpStatus.OK);
                return entity;
            }

            userService.modifyName(user.getUserId(), userDto.getName());
            entity = new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/user/modifyPwd"}, method = RequestMethod.POST)
    public ResponseEntity<String> modifyPwd(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody UserDto userDto, @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity = null;
        boolean result = false;

        try {
            if (user == null) {
                entity = new ResponseEntity(false, HttpStatus.OK);
                return entity;
            }

            userService.modfiyPassword(user.getUserId(), userDto.getPassword());
            entity = new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/user/modifyEmail"}, method = RequestMethod.POST)
    public ResponseEntity<String> modifyEmail(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody UserDto userDto, @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity = null;
        boolean result = false;

        try {
            if (user == null) {
                entity = new ResponseEntity(false, HttpStatus.OK);
                return entity;
            }

            userService.modifyEmail(user.getUserId(), userDto.getEmail());
            entity = new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
}
