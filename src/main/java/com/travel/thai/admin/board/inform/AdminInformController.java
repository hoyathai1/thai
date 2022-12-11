package com.travel.thai.admin.board.inform;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.service.BoardCategoryService;
import com.travel.thai.bbs.service.BoardInformService;
import com.travel.thai.bbs.service.BoardService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/board/inform")
public class AdminInformController {

    @Autowired
    private BoardInformService boardInformService;

    @Autowired
    private BoardCategoryService categoryService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {
        HttpSession session = request.getSession();
        String[] fileList = (String[]) session.getAttribute("fileNameList");

        if (fileList!= null && fileList.length > 0) {
            session.removeAttribute("fileNameList");
        }

        Page<BoardInformDto> list = boardInformService.searchForAdmin(search);

        model.addAttribute("list", list);
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/admin/board/inform/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            boardInformService.deleteBoardInform(search);

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

            boardInformService.restoreBoardInform(search);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> detailAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            BoardInformDto result = boardInformService.searchOneForAdmin(search);

            map.put("inform", result);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modifyAjax(HttpServletRequest request, @RequestBody BoardInform boardInform
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        HttpSession session = request.getSession();
        try {

            boardInformService.modifyBoardInform(boardInform);

            String[] fileNameList = (String[]) session.getAttribute("fileNameList");
            boardInformService.moveImage(fileNameList, boardInform);

            entity = new ResponseEntity(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerAjax(HttpServletRequest request, @RequestBody BoardInform boardInform
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        HttpSession session = request.getSession();

        try {
            boardInform.setUserId(user.getUserId());
            BoardInform result = boardInformService.save(boardInform);

            String[] fileNameList = (String[]) session.getAttribute("fileNameList");

            boardInformService.moveImage(fileNameList, result);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute("fileNameList");

        return entity;
    }

    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> typeAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            List<BoardType> result = categoryService.getBoardTypeListForAdmin(search.getCategory());
            map.put("list", result);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> categoryListAjax(HttpServletRequest request, @RequestBody Search search
            , @AuthenticationPrincipal User user) {
        ResponseEntity<Map<String, Object>> entity;
        boolean result = false;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("list", categoryService.getBoardCategoryAllList());

            entity = new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
}
