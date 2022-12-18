package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.BookMark;
import com.travel.thai.bbs.domain.BookMarkDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BookMarkRepositoryCustom;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBookMark.bookMark;
import static com.travel.thai.bbs.domain.QBoard.board;
import static com.travel.thai.bbs.domain.QComment.comment;
import static com.travel.thai.user.domain.QUser.user;

@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private static final String ALL = "all";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";

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
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory.selectOne().from(bookMark).where(bookMark.user_id.eq(search.getUserId())).fetch().size();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public PageImpl<BookMarkDto> searchBookmarkForPc(Search search, Pageable pageable) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        // 검색어 있을때
        if (StringUtils.isNotEmpty(search.getContent())) {
            if (ALL.equals(search.getKeyword()) & StringUtils.isNotEmpty(search.getContent())) {
                whereBuilder.and(board.title.contains(search.getContent()))
                        .or(board.contentsTxt.contains(search.getContent()));
            } else if (TITLE.equals(search.getKeyword())) {
                whereBuilder.and(board.title.contains(search.getContent()));
            } else if (CONTENT.equals(search.getKeyword())) {
                whereBuilder.and(board.contentsTxt.contains(search.getContent()));
            }
        }

        if (StringUtils.isNotEmpty(search.getCategory())) {
            whereBuilder.and(board.category.eq(search.getCategory()));
        }

        List<BookMarkDto> results = queryFactory
                .select(Projections.constructor(BookMarkDto.class,
                        bookMark.id,
                        bookMark.board_id,
                        board.title,
                        board.author,
                        user.name,
                        board.createDate,
                        board.category,
                        board.type,
                        board.view,
                        board.likes.size(),
                        comment.id.count().intValue()
                )).from(bookMark)
                .leftJoin(board).on(bookMark.board_id.eq(board.id))
                .leftJoin(user).on(board.userId.eq(user.userId))
                .leftJoin(comment).on(comment.board.id.eq(bookMark.board_id))
                .where(whereBuilder.and(bookMark.user_id.eq(search.getUserId())))
                .groupBy(bookMark.id)
                .orderBy(bookMark.id.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(bookMark)
                .leftJoin(board).on(bookMark.board_id.eq(board.id))
                .where(whereBuilder.and(bookMark.user_id.eq(search.getUserId())))
                .fetch().size();

        return new PageImpl<>(results, pageable, count);
    }
}
