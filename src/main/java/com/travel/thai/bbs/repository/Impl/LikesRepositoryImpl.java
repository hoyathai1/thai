package com.travel.thai.bbs.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.repository.LikesRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.travel.thai.bbs.domain.QLikes.likes;

@RequiredArgsConstructor
public class LikesRepositoryImpl implements LikesRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public int getLikesCount(Long boardId) {
        int count = queryFactory.selectOne()
                .from(likes)
                .where(likes.board.id.eq(boardId))
                .fetch().size();

        return count;
    }

    @Override
    public boolean isLikesByIp(Long boardId, String ip) {
        Integer fetchOne = queryFactory
                                .selectOne()
                                .from(likes)
                                .where(likes.board.id.eq(boardId).and(likes.ip.eq(ip)))
                                .fetchFirst();

        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }

    @Override
    public boolean isLikesByUserId(Long boardId, String userId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(likes)
                .where(likes.board.id.eq(boardId).and(likes.userId.eq(userId)))
                .fetchFirst();

        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }

    @Override
    public void deleteLikesByIp(Long boardId, String ip) {
        queryFactory.delete(likes)
                .where(likes.board.id.eq(boardId).and(likes.ip.eq(ip)))
                .execute();
    }

    @Override
    public void deleteLikesByUserId(Long boardId, String userId) {
        queryFactory.delete(likes)
                .where(likes.board.id.eq(boardId).and(likes.userId.eq(userId)))
                .execute();
    }
}
