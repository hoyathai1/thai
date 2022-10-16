package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> searchBoard(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardRepository.search(search, pageable);
    }

}
