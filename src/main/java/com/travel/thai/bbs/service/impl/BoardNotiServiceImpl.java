package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.BoardNoti;
import com.travel.thai.bbs.domain.BoardNotiDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardNotiRepository;
import com.travel.thai.bbs.service.BoardNotiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardNotiServiceImpl implements BoardNotiService {
    @Autowired
    private BoardNotiRepository boardNotiRepository;

    @Override
    public Page<BoardNotiDto> search(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), 15
        );

        return boardNotiRepository.search(search, pageable);
    }

    @Override
    public BoardNoti save(BoardNoti noti) {
        return boardNotiRepository.save(noti);
    }
}
