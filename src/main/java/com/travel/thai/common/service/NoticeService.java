package com.travel.thai.common.service;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.domain.NoticeDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {
    Notice saveNotice(Notice notice);
    List<NoticeDto> search();
    Page<NoticeDto> searchForPaging(Search search);
    void updateNotice(Notice notice);
    void activeNotice(Notice notice);
}
