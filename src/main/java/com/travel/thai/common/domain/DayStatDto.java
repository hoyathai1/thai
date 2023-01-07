package com.travel.thai.common.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
public class DayStatDto {
    int userCnt;
    int userTotal;
    int boardCnt;
    int boardTotal;
    int commentCnt;
    int commentTotal;
    int visitCnt;
    int visitDayTotal;
    int visitTotal;

    public DayStatDto() {
    }

    @Builder
    public DayStatDto(int userCnt, int boardCnt, int commentCnt) {
        this.userCnt = userCnt;
        this.boardCnt = boardCnt;
        this.commentCnt = commentCnt;
    }
}
