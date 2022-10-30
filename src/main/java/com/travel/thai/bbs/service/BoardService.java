package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.*;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<BoardDto> searchBoard(Search search);
    Board searchOne(Search search);
    Board saveBoard(Board board);
    void increseViewCount(Search search);
}
