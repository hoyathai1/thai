package com.travel.thai.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.repository.NoticeRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.travel.thai.common.domain.QNotice.notice;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Notice> search() {
        List<Notice> result = queryFactory.select(notice).from(notice).orderBy(notice.id.desc()).fetch();

        return result;
    }
}
