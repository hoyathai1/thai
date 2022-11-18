package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.BoardNoti;
import com.travel.thai.bbs.domain.BoardNotiDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.Page;

public interface BoardNotiService {
    Page<BoardNotiDto> search(Search search);
    BoardNoti save(BoardNoti noti);
}
