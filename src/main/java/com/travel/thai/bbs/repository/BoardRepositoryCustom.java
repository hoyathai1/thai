package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    PageImpl<BoardDto> search(Search search, Pageable pageable);
    PageImpl<BoardDto> searchForPcDetail(Search search, Pageable pageable);
    PageImpl<BoardDto> searchForPcInformDetail(Search search, Pageable pageable);
    PageImpl<BoardDto> searchForDetail(Search search, Pageable pageable);
    PageImpl<BoardDto> searchForInformDetail(Search search, Pageable pageable);
    BoardDto searchOne(Search search);
    void increseViewCount(Search search);
    boolean isExistBoard(long boardNum);
    Board existBoard(long boardNum);
    void deleteBoard(long boardNum);
    void restoreBoard(long boardNum);
    String getPassword(long boardNum);
    void modifyBoard(Board board);
    PageImpl<BoardDto> searchById(Search search, Pageable pageable);
    PageImpl<BoardDto> searchByIdForPc(Search search, Pageable pageable);
    BoardDto searchBoardContent(Search search);
    BoardDto searchBoardDetailForAdmin(Search search);
    PageImpl<BoardDto> searchForAdmin(Search search, Pageable pageable);
}
