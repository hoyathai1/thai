package com.travel.thai.common.service;

import com.travel.thai.common.domain.DayStatDto;

public interface DayStatService {
    void statNewUser();

    void statNewBoard();

    void statNewComment();

    DayStatDto statStatDate(String date);
    DayStatDto statStatTotal(String date);
}
