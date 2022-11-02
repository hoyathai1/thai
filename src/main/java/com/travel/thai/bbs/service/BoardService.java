package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<BoardDto> searchBoard(Search search);
    Board searchOne(Search search);
    Board saveBoard(Board board);
    void increseViewCount(Search search);
    boolean isCheckPassword (Search search);
    void deleteBoard(Search search);
    boolean modifyBoard(Board board);
}
