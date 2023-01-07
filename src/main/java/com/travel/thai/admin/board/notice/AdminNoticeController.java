package com.travel.thai.admin.board.notice;

import com.travel.thai.bbs.domain.PageDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.service.BoardCategoryService;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.domain.NoticeDto;
import com.travel.thai.common.service.EmailService;
import com.travel.thai.common.service.NoticeService;
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
import java.util.List;

@Controller
@RequestMapping("/admin/board/notice")
public class AdminNoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private BoardCategoryService bc;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute("search") Search search, @AuthenticationPrincipal User user) {

        Page<NoticeDto> list = noticeService.searchForPagingAdmin(search);
        model.addAttribute("list", list);
        model.addAttribute("category", bc.getBoardCategoryList());
        model.addAttribute("pageDto", new PageDto(list.getTotalElements(), list.getPageable()));
        model.addAttribute("search", search);

        return "/admin/board/notice/list";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseEntity<String> modifyAjax(HttpServletRequest request, @RequestBody Notice notice
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            notice.setAuthor(user.getName());
            notice.setUserId(user.getUserId());
            noticeService.updateNotice(notice);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerAjax(HttpServletRequest request, @RequestBody Notice notice
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            notice.setDel(true);
            notice.setAuthor(user.getName());
            notice.setUserId(user.getUserId());
            noticeService.saveNotice(notice);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public ResponseEntity<String> activeAjax(HttpServletRequest request, @RequestBody Notice notice
            , @AuthenticationPrincipal User user) {
        ResponseEntity<String> entity;
        boolean result = false;
        try {
            noticeService.activeNotice(notice);

            entity = new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
}
