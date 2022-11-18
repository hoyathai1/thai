package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.repository.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                .where(comment.board.id.eq(search.getBoardNum()).and(comment.isDel.isFalse()))
                .orderBy(comment.createDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(comment)
                .where(comment.upper.eq(search.getBoardNum()).and(comment.isDel.isFalse()))
                .fetch().size();

        // 대댓글 isDel 처리
        for (int i=0; i < results.size(); i++) {
            List<Comment> child = new ArrayList<>();

            Comment c = results.get(i);

            for (Comment c2 : c.getChildren()) {
                if (!c2.isDel()) {
                    child.add(c2);
                }
            }

            results.get(i).setChildren(child);
        }

        commentDto.setList(new PageImpl<>(results, pageable, count));
        commentDto.setCommentTotalCount(count);

        return commentDto;
    }

    @Override
    public String searchPassword(Search search) {
        String result = queryFactory.select(comment.password)
                                    .from(comment)
                                    .where(comment.id.eq(search.getCommentNum()))
                                    .fetchOne();

        return result;
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        queryFactory.update(comment)
                .set(comment.isDel, true)
                .where(comment.id.eq(id).or(comment.parent.id.eq(id)))
                .execute();

    }

    @Override
    public String searchForUserId(Long id) {
        String userId = queryFactory
                            .select(comment.userId)
                            .from(comment)
                            .where(comment.id.eq(id))
                            .fetchOne();
        return userId;
    }

}
