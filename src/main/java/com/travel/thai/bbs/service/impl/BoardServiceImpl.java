package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> searchBoard(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );


        if (StringUtils.isNotEmpty(search.getContent())) {
            // 검색
            return boardRepository.searchByKeyword(search, pageable);
        }

        return boardRepository.search(search, pageable);
    }

    @Override
    public Board searchOne(Search search) {
        return boardRepository.searchOne(search);
    }



    @Override
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public void increseViewCount(Search search) {
        boardRepository.increseViewCount(search);
    }
}
