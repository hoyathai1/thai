package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.BoardFileDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardInformFileRepository;
import com.travel.thai.bbs.service.BoardInformFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardInformFileServiceImpl implements BoardInformFileService {
    @Autowired
    private BoardInformFileRepository boardInformFileRepository;

    @Override
    public Page<BoardFileDto> getImageListForAdmin(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardInformFileRepository.getImageListForAdmin(search, pageable);
    }

    @Override
    @Transactional
    public void deleteImage(Search search) {
        boardInformFileRepository.deleteImage(search);
    }

    @Override
    @Transactional
    public void restoreImage(Search search) {
        boardInformFileRepository.restoreImage(search);
    }
}
