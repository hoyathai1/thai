package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.user.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    Page<BoardDto> searchBoard(Search search);
    Page<BoardDto> searchBoardForPcDetail(Search search);
    Page<BoardDto> searchBoardForPcInformDetail(Search search);
    Page<BoardDto> searchBoardForDetail(Search search);
    Page<BoardDto> searchBoardForInformDetail(Search search);
    BoardDto searchOne(Search search);
    Board saveBoard(Board board);
    void increseViewCount(Search search);
    boolean isCheckPassword (Search search);
    void deleteBoard(Search search);
    void restoreBoard(Search search);
    boolean modifyBoard(Board board, User user);
    boolean modifyBoardForAdmin(Board board);
    void moveImage(String[] fileNames, Board board);
    BoardDto searchBoardContents(Search search);
    BoardDto searchBoardDetailForAdmin(Search search);
    Page<BoardDto> searchBoardForAdmin(Search search);
    Page<BoardDto> searchByIdForPc(Search search);
}
