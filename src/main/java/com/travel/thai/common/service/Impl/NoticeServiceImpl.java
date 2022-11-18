package com.travel.thai.common.service.Impl;

import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.repository.NoticeRepository;
import com.travel.thai.common.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public Notice saveNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public List<Notice> search() {
        return noticeRepository.search();
    }
}
