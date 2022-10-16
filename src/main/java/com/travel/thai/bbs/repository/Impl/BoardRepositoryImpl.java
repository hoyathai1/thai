package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageImpl<Board> search(Search search, Pageable pageable) {
        List<Board> results = queryFactory
                .select(Projections.constructor(Board.class,
                        board.id,
                        board.title,
                        board.author,
                        board.createDate,
                        board.view
                ))
                .from(board)
                .orderBy(board.createDate.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory
                .select(Projections.constructor(Board.class,
                        board.id,
                        board.title,
                        board.author,
                        board.createDate,
                        board.view
                ))
                .from(board)
                .orderBy(board.createDate.desc())
                .fetch().size();

        return new PageImpl<>(results, pageable, count);
    }
}
