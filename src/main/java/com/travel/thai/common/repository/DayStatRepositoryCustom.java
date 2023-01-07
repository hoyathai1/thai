package com.travel.thai.common.repository;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.domain.DayStatDto;

public interface DayStatRepositoryCustom {

    void statNewUser();

    void statNewBoard();

    void statNewComment();

    DayStatDto dayStatDate(String date);
    DayStatDto dayStatTime(String time);

    DayStatDto dayStatTotal();
}