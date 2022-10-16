package com.travel.thai.bbs.controller;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board/*")
public class BoardController {
    @Autowired
    BoardService boardService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "board/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getList(@RequestBody Search search) {
        System.out.println("" + search.getPageNum() +"|"+ search.getPageSize());
        ResponseEntity<Map<String, Object>> entity;

        try {
            Map<String, Object> map = new HashMap<>();
            Page<Board> list = boardService.searchBoard(search);
            map.put("list", list);

            entity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

}
