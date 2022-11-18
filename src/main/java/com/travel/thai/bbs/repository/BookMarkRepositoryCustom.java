package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BookMark;

public interface BookMarkRepositoryCustom {
    Boolean isBookMark(BookMark param);
    void deleteBookMark(BookMark param);
}
