package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardNotiDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardNotiRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoard.board;
import static com.travel.thai.bbs.domain.QBoardNoti.boardNoti;
import static com.travel.thai.bbs.domain.QComment.comment;


@RequiredArgsConstructor
public class BoardNotiRepositoryImpl implements BoardNotiRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public PageImpl<BoardNotiDto> search(Search search, Pageable pageable) {
        List<BoardNotiDto> results = queryFactory
                .select(Projections.constructor(BoardNotiDto.class,
                        boardNoti.id,
                        board.id,
                        comment.id,
                        board.title,
                        comment.content,
                        comment.author,
                        comment.ip,
                        comment.isUser,
                        boardNoti.createDate,
                        board.category
                        ))
                .from(boardNoti)
                .leftJoin(board).on(boardNoti.board_id.eq(board.id).and(board.isDel.isFalse()))
                .leftJoin(comment).on(boardNoti.comment_id.eq(comment.id).and(comment.isDel.isFalse()))
                .where(boardNoti.user_id.eq(search.getUserId()).and(boardNoti.isRead.isFalse()).and(board.isDel.isFalse()).and(comment.isDel.isFalse()))
                .orderBy(boardNoti.createDate.desc())
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(boardNoti)
                .leftJoin(board).on(boardNoti.board_id.eq(board.id).and(board.isDel.isFalse()))
                .leftJoin(comment).on(boardNoti.comment_id.eq(comment.id).and(comment.isDel.isFalse()))
                .where(boardNoti.user_id.eq(search.getUserId()).and(boardNoti.isRead.isFalse()).and(board.isDel.isFalse()).and(comment.isDel.isFalse()))
                .orderBy(boardNoti.createDate.desc())
                .fetch().size();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public void allDeleteNoti(Search search) {
        queryFactory.update(boardNoti)
                .set(boardNoti.isRead, true)
                .where(boardNoti.user_id.eq(search.getUserId()))
                .execute();
    }

    @Override
    public void deleteNoti(Search search) {
        queryFactory.update(boardNoti)
                .set(boardNoti.isRead, true)
                .where(boardNoti.user_id.eq(search.getUserId()).and(boardNoti.id.eq(search.getBoardNotiId())))
                .execute();
    }

    @Override
    public boolean isHasNoti(Search search) {
        int count = queryFactory
                .selectOne()
                .from(boardNoti)
                .leftJoin(board).on(boardNoti.board_id.eq(board.id).and(board.isDel.isFalse()))
                .leftJoin(comment).on(boardNoti.comment_id.eq(comment.id).and(comment.isDel.isFalse()))
                .where(boardNoti.user_id.eq(search.getUserId()).and(boardNoti.isRead.isFalse()).and(board.isDel.isFalse()).and(comment.isDel.isFalse()))
                .orderBy(boardNoti.createDate.desc())
                .fetch().size();

        return count > 0;
    }
}
