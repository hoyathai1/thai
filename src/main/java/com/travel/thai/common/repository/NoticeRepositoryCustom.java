package com.travel.thai.common.repository;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.domain.NoticeDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeRepositoryCustom {
    List<NoticeDto> search();
    PageImpl<NoticeDto> searchForPaging(Search search, Pageable pageable);
    void updateNotice(Notice param);
    void activeNotice(Notice param);
}
