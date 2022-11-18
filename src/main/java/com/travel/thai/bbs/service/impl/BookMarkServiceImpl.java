package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.BookMark;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.repository.BookMarkRepository;
import com.travel.thai.bbs.service.BoardService;
import com.travel.thai.bbs.service.BookMarkService;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookMarkServiceImpl implements BookMarkService {
    @Autowired
    private BookMarkRepository bookMarkRepository;

    @Override
    public boolean save(BookMark bookMark) {
        BookMark result = bookMarkRepository.save(bookMark);
        return result != null;
    }

    @Override
    public Boolean isBookMark(BookMark bookMark) {
        return bookMarkRepository.isBookMark(bookMark);
    }

    @Override
    @Transactional
    public boolean delete(BookMark bookMark) {
        bookMarkRepository.deleteBookMark(bookMark);
        return true;
    }
}
