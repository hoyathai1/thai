package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardNotiDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface BoardNotiRepositoryCustom {
    PageImpl<BoardNotiDto> search(Search search, Pageable pageable);
    boolean isHasNoti(Search search);
    void allDeleteNoti(Search search);
    void deleteNoti(Search search);
}
