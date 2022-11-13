package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    PageImpl<BoardDto> search(Search search, Pageable pageable);
    BoardDto searchOne(Search search);
    void increseViewCount(Search search);
    boolean isExistBoard(long boardNum);
    Board existBoard(long boardNum);
    void deleteBoard(long boardNum);
    String getPassword(long boardNum);
    void modifyBoard(Board board);
}
