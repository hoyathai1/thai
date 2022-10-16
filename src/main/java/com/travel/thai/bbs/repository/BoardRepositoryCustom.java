package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    PageImpl<Board> search(Search search, Pageable pageable);
}
