package com.travel.thai.bbs.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BookMark;
import com.travel.thai.bbs.repository.BookMarkRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.travel.thai.bbs.domain.QBookMark.bookMark;

@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean isBookMark(BookMark param) {
        BookMark result = queryFactory.select(bookMark).from(bookMark).where(bookMark.board_id.eq(param.getBoard_id()).and(bookMark.user_id.eq(param.getUser_id()))).fetchFirst();

        return result != null;
    }

    @Override
    public void deleteBookMark(BookMark param) {
        queryFactory.delete(bookMark).where(bookMark.board_id.eq(param.getBoard_id()).and(bookMark.user_id.eq(param.getUser_id()))).execute();
    }
}
