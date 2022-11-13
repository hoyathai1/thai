package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.LikesDto;

public interface LikesService {
    boolean likeThePost(LikesDto likesDto);
    int getLikesCount(long boardId);
    boolean isLikesByIp(long boardId, String ip);
    boolean isLikesByUserId(long boardId, String userId);
}
