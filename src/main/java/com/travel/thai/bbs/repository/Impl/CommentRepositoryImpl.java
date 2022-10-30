package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.repository.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public CommentDto search(Search search, Pageable pageable) {
        CommentDto commentDto = new CommentDto();

        QComment comment1 = new QComment("comment1");
        QComment comment2 = new QComment("comment2");

        List<Comment> results = queryFactory
                .selectFrom(comment)
                .where(comment.board.id.eq(search.getBoardNum()))
                .orderBy(comment.createDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(comment1)
                .leftJoin(comment2).on(comment1.id.eq(comment2.parent.id))
                .where(comment1.upper.eq(search.getBoardNum()))
                .groupBy(comment1.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().size();

        commentDto.setList(new PageImpl<>(results, pageable, count));
        commentDto.setCommentTotalCount(count);

        return commentDto;
    }
}
