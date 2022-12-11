package com.travel.thai.admin.image;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.BoardFileService;
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

@Controller
@RequestMapping("/admin/image")
public class AdminImageController {

    @Autowired
    private BoardFileService boardFileService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        Page<BoardFileDto> list = boardFileService.getImageListForAdmin(search);

        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/admin/image/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {

            boardFileService.deleteImage(search);

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

            boardFileService.restoreImage(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
}
