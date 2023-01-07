
package com.travel.thai.common.service.Impl;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.domain.Visit;
import com.travel.thai.common.repository.BannerRepository;
import com.travel.thai.common.repository.VisitRepository;
import com.travel.thai.common.service.BannerService;
import com.travel.thai.common.service.VisitService;
import com.travel.thai.common.util.DateUtil;
import com.travel.thai.common.util.HttpUtil;
import com.travel.thai.common.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service("visitService")
public class visitServiceImpl implements VisitService {
    @Autowired
    private VisitRepository visitRepository;

    @Override
    public Visit save(HttpServletRequest req) {
        Visit visit = new Visit();
        visit.setIp(LogUtil.etRemoteAddr(req));
        visit.setBrowser(HttpUtil.getBrowserInfo(req));
        visit.setDevice(HttpUtil.getDevice(req));
        visit.setVisitDate(DateUtil.today_date());
        visit.setVisitTime(DateUtil.today_time());

        return visitRepository.save(visit);
    }

    @Override
    public int visitDate(String date) {
        return visitRepository.visitDate(date);
    }
}
