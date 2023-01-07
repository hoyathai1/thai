package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.*;
import com.travel.thai.common.domain.DayStat;
import com.travel.thai.common.service.DayStatService;
import com.travel.thai.common.util.HttpUtil;
import com.travel.thai.user.domain.User;
import org.springframework.data.domain.Page;
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

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardNotiRepository boardNotiRepository;

    @Autowired
    private DayStatService dayStatService;

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
            comment.setAuthor(HttpUtil.convertHtmlStr((commentDto.getAuthor())));

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

        String content = commentDto.getContent();
        content = HttpUtil.convertHtmlStr(content);
        content = content.replaceAll("\n", "</br>");

        comment.setContent(content);
        comment.setUpper(commentDto.getBoardId());
        comment.setIp(commentDto.getIp());

        Comment result = commentRepository.save(comment);
        dayStatService.statNewComment();

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

    @Override
    public Page<CommentDto> searchForAdmin(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), 15
        );

        return commentRepository.searchForAdmin(search, pageable);
    }

    @Override
    @Transactional
    public void deleteComment(Search search) {
        commentRepository.deleteComment(search);
    }

    @Override
    @Transactional
    public void restoreComment(Search search) {
        commentRepository.restoreComment(search);
    }

    @Override
    @Transactional
    public void modifyContent(CommentDto dto) {
        commentRepository.modifyContent(dto);
    }


    @Override
    public CommentDto searchListForAdmin(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), 100
        );

        return commentRepository.searchListForAdmin(search, pageable);
    }

    @Override
    public Page<CommentDto> searchListForPc(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), 50
        );

        return commentRepository.searchListForPc(search, pageable);
    }
}
