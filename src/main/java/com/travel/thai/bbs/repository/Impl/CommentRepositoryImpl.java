package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.repository.CommentRepositoryCustom;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.travel.thai.bbs.domain.QBoard.board;
import static com.travel.thai.user.domain.QUser.user;
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

    @Override
    public PageImpl<CommentDto> searchForAdmin(Search search, Pageable pageable) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        // 검색어 있을때
        if (StringUtils.isNotEmpty(search.getContent())) {
            whereBuilder.and(comment.content.eq(search.getContent()));
        }

        List<CommentDto> result = queryFactory
                .select(Projections.constructor(CommentDto.class,
                        comment.id,
                        comment.author,
                        comment.content,
                        comment.upper,
                        comment.parent.id,
                        comment.ip,
                        user.name,
                        comment.isUser,
                        comment.isDel,
                        comment.children.size(),
                        comment.createDate
                        ))
                .from(comment)
                .leftJoin(user).on(user.userId.eq(comment.userId))
                .orderBy(comment.createDate.desc())
                .offset(pageable.getOffset())         // 페이지 번호
                .limit(pageable.getPageSize())        // 페이지 사이즈
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(comment)
                .orderBy(comment.createDate.desc())
                .fetch().size();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public void deleteComment(Search search) {
        queryFactory
                .update(comment)
                .set(comment.isDel, true)
                .where(comment.id.eq(search.getCommentNum()))
                .execute();
    }

    @Override
    public void restoreComment(Search search) {
        queryFactory
                .update(comment)
                .set(comment.isDel, false)
                .where(comment.id.eq(search.getCommentNum()))
                .execute();
    }

    @Override
    public void modifyContent(CommentDto dto) {
        queryFactory
                .update(comment)
                .set(comment.content, dto.getContent())
                .where(comment.id.eq(dto.getId()))
                .execute();
    }

    @Override
    public CommentDto searchListForAdmin(Search search, Pageable pageable) {
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
                .from(comment)
                .where(comment.upper.eq(search.getBoardNum()))
                .fetch().size();

        commentDto.setList(new PageImpl<>(results, pageable, count));
        commentDto.setCommentTotalCount(count);

        return commentDto;
    }
}
