
package com.travel.thai.common.service.Impl;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.domain.DayStat;
import com.travel.thai.common.domain.DayStatDto;
import com.travel.thai.common.repository.BannerRepository;
import com.travel.thai.common.repository.DayStatRepository;
import com.travel.thai.common.service.BannerService;
import com.travel.thai.common.service.DayStatService;
import com.travel.thai.common.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DayStatServiceImpl implements DayStatService {
    @Autowired
    private DayStatRepository repository;

    @Autowired
    private VisitService visitService;

    @Override
    @Transactional
    public void statNewUser() {
        repository.statNewUser();
    }

    @Override
    @Transactional
    public void statNewBoard() {
        repository.statNewBoard();
    }

    @Override
    @Transactional
    public void statNewComment() {
        repository.statNewComment();
    }

    @Override
    public DayStatDto statStatDate(String date) {
        DayStatDto dto = repository.dayStatDate(date);
        dto.setVisitCnt(visitService.visitDate(date));

        return dto;
    }

    @Override
    public DayStatDto statStatTotal(String date) {
        return repository.dayStatTotal();
    }
}
