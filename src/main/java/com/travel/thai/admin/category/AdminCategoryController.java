package com.travel.thai.admin.category;


import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.BoardCategoryService;
import com.travel.thai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private BoardCategoryService boardCategoryService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {

        List<BoardCategory> list = boardCategoryService.getBoardCategoryAllList();

        model.addAttribute("list", list);

        return "/admin/category/list";
    }


    @RequestMapping(value = {"/category"}, method = RequestMethod.GET)
    public String category(HttpServletRequest request, HttpServletResponse response, Model model, @AuthenticationPrincipal User user) {

        List<BoardCategory> list = boardCategoryService.getBoardCategoryAllList();

        model.addAttribute("list", list);

        return "/admin/category/category";
    }

    @RequestMapping(value = {"/category/detail"}, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> categoryViewAjax(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            BoardCategory result = boardCategoryService.getBoardCategory(search.getCategory());

            map.put("category", result);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/category/modify"}, method = RequestMethod.POST)
    public ResponseEntity<String> categoryModifyAjax(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody BoardCategory boardCategory
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;

        try {
            boardCategoryService.modifyCategory(boardCategory);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/category/register"}, method = RequestMethod.POST)
    public ResponseEntity<String> categoryRegisterAjax(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody BoardCategory boardCategory
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;

        try {
            boardCategoryService.registerCategory(boardCategory);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/category/unuse", method = RequestMethod.POST)
    public ResponseEntity<String> categoryUnUseAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            boardCategoryService.unuseCategory(search.getCategory());

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/category/use", method = RequestMethod.POST)
    public ResponseEntity<String> categoryUseAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            boardCategoryService.useCategory(search.getCategory());

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/type"}, method = RequestMethod.GET)
    public String type(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        List<BoardType> list = boardCategoryService.getBoardTypeList(search.getCategory());

        model.addAttribute("list", list);

        return "/admin/category/type";
    }

    @RequestMapping(value = {"/type/detail"}, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> typeViewAjax(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody BoardType boardType
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            BoardType result = boardCategoryService.getBoardType(boardType.getId());

            map.put("type", result);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/type/modify"}, method = RequestMethod.POST)
    public ResponseEntity<String> typeModifyAjax(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody BoardType boardType
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;

        try {
            boardCategoryService.modifyType(boardType);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = {"/type/register"}, method = RequestMethod.POST)
    public ResponseEntity<String> typeRegisterAjax(HttpServletRequest request, HttpServletResponse response, Model model, @RequestBody BoardTypeDto boardTypeDto
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;

        try {
            BoardCategory bc = new BoardCategory();
            bc.setId(boardTypeDto.getCategoryId());
            BoardType boardType = new BoardType();
            boardType.setBoardCategory(bc);
            boardType.setName(boardTypeDto.getName());
            boardType.setType(boardTypeDto.getType());
            boardType.setOrderBy(boardTypeDto.getOrderBy());
            boardType.setUse(false);
            boardCategoryService.registerType(boardType);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/type/unuse", method = RequestMethod.POST)
    public ResponseEntity<String> typeUnUseAjax(HttpServletRequest request, @RequestBody BoardType boardType
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            boardCategoryService.unuseType(boardType.getId());

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/type/use", method = RequestMethod.POST)
    public ResponseEntity<String> typeUseAjax(HttpServletRequest request, @RequestBody BoardType boardType
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            boardCategoryService.useType(boardType.getId());

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

}
