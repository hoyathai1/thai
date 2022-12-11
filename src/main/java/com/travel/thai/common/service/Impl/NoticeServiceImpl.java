package com.travel.thai.common.service.Impl;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.domain.NoticeDto;
import com.travel.thai.common.repository.NoticeRepository;
import com.travel.thai.common.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<NoticeDto> search() {
        return noticeRepository.search();
    }

    @Override
    public Page<NoticeDto> searchForPaging(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), 15
        );

        return noticeRepository.searchForPaging(search, pageable);
    }

    @Override
    @Transactional
    public void updateNotice(Notice notice) {
        noticeRepository.updateNotice(notice);
    }

    @Override
    @Transactional
    public void activeNotice(Notice notice) {
        noticeRepository.activeNotice(notice);
    }
}
