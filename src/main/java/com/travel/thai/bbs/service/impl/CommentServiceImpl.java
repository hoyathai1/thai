package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.bbs.repository.BoardNotiRepository;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.repository.CommentRepository;
import com.travel.thai.bbs.service.CommentService;
import com.travel.thai.common.util.RSAUtil;
import com.travel.thai.common.util.StringUtils;
import com.travel.thai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardNotiRepository boardNotiRepository;

    @Override
    public Comment saveComment(CommentDto commentDto, User user) {
        Board board = new Board();
        board.setId(commentDto.getBoardId());

        Comment comment = new Comment();

        if (user != null) {
            comment.setUserId(user.getUserId());
            comment.setUser(true);
            comment.setAuthor(user.getName());
        } else {
            comment.setAuthor(commentDto.getAuthor());

            // password
            try {
                String encPassword = RSAUtil.encryptRSA(commentDto.getPassword());
                comment.setPassword(encPassword);
            } catch (Exception e) {
                // TODO: 예외처리
            }
        }

        if (commentDto.getParentId() != null) {
            Comment parent = new Comment();
            parent.setId(commentDto.getParentId());
            comment.setParent(parent);
            comment.setBoard(null);
        } else {
            comment.setBoard(board);
        }

        comment.setContent(commentDto.getContent());
        comment.setUpper(commentDto.getBoardId());
        comment.setIp(commentDto.getIp());

        Comment result = commentRepository.save(comment);


        Search search = new Search();
        search.setBoardNum(commentDto.getBoardId());
        BoardDto boardDto = boardRepository.searchOne(search);

        // NOTIFICATION SETTING
        if (user != null) {
            if (!user.getUserId().equals(boardDto.getUserId())) {
                BoardNoti boardNoti = new BoardNoti();
                boardNoti.setBoard_id(commentDto.getBoardId());
                boardNoti.setComment_id(result.getId());
                boardNoti.setUser_id(boardDto.getUserId());
                boardNoti.setType("COMMENT");
                boardNotiRepository.save(boardNoti);
            }
        } else {
            if (StringUtils.isNotEmpty(boardDto.getUserId())) {
                BoardNoti boardNoti = new BoardNoti();
                boardNoti.setBoard_id(commentDto.getBoardId());
                boardNoti.setComment_id(result.getId());
                boardNoti.setUser_id(boardDto.getUserId());
                boardNoti.setType("COMMENT");
                boardNotiRepository.save(boardNoti);
            }
        }

        // 대댓글도 NOTIFICATION 추가
        if (commentDto.getParentId() != null) {
            String parentCommentId = commentRepository.searchForUserId(commentDto.getParentId());

            if (StringUtils.isNotEmpty(parentCommentId) && !parentCommentId.equals(boardDto.getUserId())) {
                BoardNoti boardNoti = new BoardNoti();
                boardNoti.setBoard_id(commentDto.getBoardId());
                boardNoti.setComment_id(result.getId());
                boardNoti.setUser_id(parentCommentId);
                boardNoti.setType("COMMENT");
                boardNotiRepository.save(boardNoti);
            }
        }


        return result;
    }

    @Override
    public CommentDto search(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return commentRepository.search(search, pageable);
    }

    @Override
    public boolean deleteComment(Search search, User user) {
        boolean result = false;

        try {
            if (user != null) {
                String userId = commentRepository.searchForUserId(search.getCommentNum());

                if (user.getUserId().equals(userId)) {
                    commentRepository.deleteComment(search.getCommentNum());
                    result = true;
                }
            } else {
                String dbPassword = commentRepository.searchPassword(search);
                String decDbPassword = RSAUtil.decryptRSA(dbPassword);

                if (decDbPassword.equals(search.getPassword())) {
                    commentRepository.deleteComment(search.getCommentNum());
                    result = true;
                }
            }
        } catch (Exception e) {
            // TODO: 예외처리
            System.out.println("error");
        }

        return result;
    }
}
