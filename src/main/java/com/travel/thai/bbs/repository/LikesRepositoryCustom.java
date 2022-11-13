package com.travel.thai.bbs.repository;

public interface LikesRepositoryCustom {
    int getLikesCount(Long boardId);
    boolean isLikesByIp(Long boardId, String ip);
    boolean isLikesByUserId(Long boardId, String userId);
    void deleteLikesByIp(Long boardId, String ip);
    void deleteLikesByUserId(Long boardId, String ip);
}
