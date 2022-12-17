package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.BookMark;
import com.travel.thai.bbs.domain.BookMarkDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BookMarkRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBookMark.bookMark;
import static com.travel.thai.bbs.domain.QBoard.board;

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

    @Override
    public PageImpl<BookMarkDto> searchBookmark(Search search, Pageable pageable) {
        List<BookMarkDto> results = queryFactory
                .select(Projections.constructor(BookMarkDto.class,
                    bookMark.id,
                    bookMark.board_id,
                    board.title,
                    board.author,
                    board.createDate,
                    board.category
                )).from(bookMark)
                .leftJoin(board).on(bookMark.board_id.eq(board.id))
                .where(bookMark.user_id.eq(search.getUserId()))
                .fetch();

        int count = queryFactory.selectOne().from(bookMark).where(bookMark.user_id.eq(search.getUserId())).fetch().size();

        return new PageImpl<>(results, pageable, count);
    }



}
