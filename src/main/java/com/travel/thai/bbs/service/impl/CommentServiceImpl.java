package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.repository.CommentRepository;
import com.travel.thai.bbs.service.CommentService;
import com.travel.thai.common.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(CommentDto commentDto) {
        Board board = new Board();
        board.setId(commentDto.getBoardId());

        Comment comment = new Comment();
        comment.setAuthor(commentDto.getAuthor());
//        comment.setPassword(commentDto.getPassword());
        comment.setContent(commentDto.getContent());
        comment.setUpper(commentDto.getBoardId());
        comment.setIp(commentDto.getIp());

        if (commentDto.getParentId() != null) {
            Comment parent = new Comment();
            parent.setId(commentDto.getParentId());
            comment.setParent(parent);
            comment.setBoard(null);
        } else {
            comment.setBoard(board);
        }

        // password
        try {
            String encPassword = RSAUtil.encryptRSA(commentDto.getPassword());
            comment.setPassword(encPassword);
        } catch (Exception e) {
            // TODO: 예외처리
        }

        return commentRepository.save(comment);
    }

    @Override
    public CommentDto search(Search search) {

        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return commentRepository.search(search, pageable);
    }

    @Override
    public boolean deleteComment(Search search) {
        boolean result = false;

        try {
            String dbPassword = commentRepository.searchPassword(search);
            String decDbPassword = RSAUtil.decryptRSA(dbPassword);

            if (decDbPassword.equals(search.getPassword())) {
                commentRepository.deleteComment(search.getCommentNum());
                result = true;
            }

        } catch (Exception e) {
            // TODO: 예외처리
            System.out.println("error");
        }

        return result;
    }
}
