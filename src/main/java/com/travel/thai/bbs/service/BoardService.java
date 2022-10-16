package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<Board> searchBoard(Search search);
}
