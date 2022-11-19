package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BookMark;
import com.travel.thai.bbs.domain.BookMarkDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface BookMarkRepositoryCustom {
    Boolean isBookMark(BookMark param);
    void deleteBookMark(BookMark param);
    PageImpl<BookMarkDto> searchBookmark(Search search, Pageable pageable);
}
