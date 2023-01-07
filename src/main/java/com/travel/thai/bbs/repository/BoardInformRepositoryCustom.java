package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardInform;
import com.travel.thai.bbs.domain.BoardInformDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardInformRepositoryCustom {
    List<BoardInformDto> search(Search search);
    PageImpl<BoardInformDto> searchForAdmin(Search search, Pageable pageable);
    PageImpl<BoardInformDto> searchForBanner(Search search, Pageable pageable);
    BoardInformDto searchOne(Search search);
    BoardInformDto searchOneForAdmin(Search search);
    BoardInformDto searchBannerOneForAdmin(Search search);
    void modifyBoardInform(BoardInform boardInform);
    void deleteBoardInform(Search search);
    void restoreBoardInform(Search search);
}
