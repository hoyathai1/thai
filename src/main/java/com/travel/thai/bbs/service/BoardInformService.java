package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.BoardInform;
import com.travel.thai.bbs.domain.BoardInformDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardInformService {
    List<BoardInformDto> search(Search search);

    Page<BoardInformDto> searchForAdmin(Search search);

    BoardInformDto searchOne(Search search);

    BoardInformDto searchOneForAdmin(Search search);

    void modifyBoardInform(BoardInform boardInform);

    void deleteBoardInform(Search search);

    void restoreBoardInform(Search search);

    BoardInform save(BoardInform boardInform);

    void moveImage(String[] fileNames, BoardInform boardInform);
}
