package com.travel.thai.common.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.common.domain.DayStat;
import com.travel.thai.common.domain.DayStatDto;
import com.travel.thai.common.repository.DayStatRepositoryCustom;
import com.travel.thai.common.util.DateUtil;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.travel.thai.bbs.domain.QBoard.board;
import static com.travel.thai.bbs.domain.QBoardCategory.boardCategory;
import static com.travel.thai.bbs.domain.QComment.comment;
import static com.travel.thai.common.domain.QDayStat.dayStat;
import static com.travel.thai.common.domain.QVisit.visit;
import static com.travel.thai.user.domain.QUser.user;

@RequiredArgsConstructor
public class DayStatRepositoryImpl implements DayStatRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public void statNewUser() {
        queryFactory.update(dayStat).set(dayStat.newUser, dayStat.newUser.add(1)).where(dayStat.statDate.eq(DateUtil.today_date())).execute();
    }

    @Override
    public void statNewBoard() {
        queryFactory.update(dayStat).set(dayStat.newBoard, dayStat.newBoard.add(1)).where(dayStat.statDate.eq(DateUtil.today_date())).execute();
    }

    @Override
    public void statNewComment() {
        queryFactory.update(dayStat).set(dayStat.newComment, dayStat.newComment.add(1)).where(dayStat.statDate.eq(DateUtil.today_date())).execute();
    }

    @Override
    public DayStatDto dayStatDate(String date) {
        DayStatDto dto = queryFactory.select(Projections.constructor(DayStatDto.class,
                    dayStat.newUser,
                    dayStat.newBoard,
                    dayStat.newComment
                ))
                .from(dayStat)
                .where(dayStat.statDate.eq(date))
                .fetchOne();

        int visitDayToral = queryFactory.selectOne().from(visit).where(visit.visitDate.eq(date)).fetch().size();
        dto.setVisitDayTotal(visitDayToral);

        return dto;
    }

    @Override
    public DayStatDto dayStatTime(String time) {
        return null;
    }

    @Override
    public DayStatDto dayStatTotal() {
        int boardTotal = queryFactory.selectOne().from(board).fetch().size();
        int commentTotal = queryFactory.selectOne().from(comment).fetch().size();
        int userTotal = queryFactory.selectOne().from(user).fetch().size();
        int visitTotal = queryFactory.selectOne().from(visit).fetch().size();

        DayStatDto dto = new DayStatDto();
        dto.setBoardTotal(boardTotal);
        dto.setCommentTotal(commentTotal);
        dto.setUserTotal(userTotal);
        dto.setVisitTotal(visitTotal);

        return dto;
    }
}