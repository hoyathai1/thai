package com.travel.thai.common.service;

import com.travel.thai.common.domain.Notice;

import java.util.List;

public interface NoticeService {
    Notice saveNotice(Notice notice);
    List<Notice> search();
}
