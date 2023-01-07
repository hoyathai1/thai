package com.travel.thai.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.repository.BannerRepositoryCustom;
import com.travel.thai.common.repository.VisitRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.travel.thai.common.domain.QVisit.visit;

@RequiredArgsConstructor
public class VisitRepositoryImpl implements VisitRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public int visitDate(String date) {
        int count = queryFactory.selectOne().from(visit).where(visit.visitDate.eq(date)).fetch().size();

        return count;
    }
}