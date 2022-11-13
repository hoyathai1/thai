package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Likes;
import com.travel.thai.bbs.domain.LikesDto;
import com.travel.thai.bbs.repository.LikesRepository;
import com.travel.thai.bbs.service.LikesService;
import com.travel.thai.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikesServiceImpl implements LikesService {
    @Autowired
    private LikesRepository likesRepository;

    @Override
    @Transactional
    public boolean likeThePost(LikesDto likesDto) {
        boolean result = false;
        Likes likes = new Likes();
        Board board = new Board();
        board.setId(likesDto.getBoardId());

        if (StringUtils.isNotEmpty(likesDto.getUserId())) {
            // 회원 좋아요
            if (likesRepository.isLikesByUserId(likesDto.getBoardId(), likesDto.getUserId())) {
                likesRepository.deleteLikesByUserId(likesDto.getBoardId(), likesDto.getIp());

                result = false;
            } else {
                // 좋아요 없으면 추가 - true
                likes.setUserId(likesDto.getUserId());
                likes.setBoard(board);

                likesRepository.save(likes);
                result = true;
            }
        } else {
            // 비회원 좋아요
            if (likesRepository.isLikesByIp(likesDto.getBoardId(), likesDto.getIp())) {
                // 좋아요 있으면 삭제 - false
                likesRepository.deleteLikesByIp(likesDto.getBoardId(), likesDto.getIp());

                result = false;
            } else {
                // 좋아요 없으면 추가 - true
                likes.setIp(likesDto.getIp());
                likes.setBoard(board);

                likesRepository.save(likes);
                result = true;
            }
        }

        return result;
    }

    @Override
    public int getLikesCount(long boardId) {
        return likesRepository.getLikesCount(boardId);
    }

    /**
     * 좋아요 있으면 true
     *      없으면 false
     *
     * @param boardId
     * @param ip
     * @return
     */
    @Override
    public boolean isLikesByIp(long boardId, String ip) {
        return likesRepository.isLikesByIp(boardId, ip);
    }

    @Override
    public boolean isLikesByUserId(long boardId, String userId) {
        return likesRepository.isLikesByUserId(boardId, userId);
    }
}
