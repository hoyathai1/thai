package com.travel.thai.common.service;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.domain.Visit;

import javax.servlet.http.HttpServletRequest;

public interface VisitService {
    Visit save(HttpServletRequest req);
    int visitDate(String date);
}
