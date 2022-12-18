package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.user.domain.User;
import org.springframework.data.domain.Page;

public interface BookMarkService {
    boolean save(BookMark bookMark);
    boolean delete(BookMark bookMark);
    Boolean isBookMark(BookMark bookMark);
    Page<BookMarkDto> searchBoard(Search search);
    Page<BookMarkDto> searchBoardForPc(Search search);
}
