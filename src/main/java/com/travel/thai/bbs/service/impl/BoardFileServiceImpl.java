package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.repository.BoardFileRepository;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.service.BoardFileService;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class BoardFileServiceImpl implements BoardFileService {
    @Autowired
    private BoardFileRepository boardFileRepository;

    @Override
    public Page<BoardFileDto> getImageListForAdmin(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardFileRepository.getImageListForAdmin(search, pageable);
    }

    @Override
    @Transactional
    public void deleteImage(Search search) {
        boardFileRepository.deleteImage(search);
    }

    @Override
    @Transactional
    public void restoreImage(Search search) {
        boardFileRepository.restoreImage(search);
    }
}
