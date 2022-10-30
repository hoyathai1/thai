package com.travel.thai.bbs.service.impl;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.bbs.repository.CommentRepository;
import com.travel.thai.bbs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
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
        comment.setPassword(commentDto.getPassword());
        comment.setContent(commentDto.getContent());
        comment.setUpper(commentDto.getBoardId());

        if (commentDto.getParentId() != null) {
            Comment parent = new Comment();
            parent.setId(commentDto.getParentId());
            comment.setParent(parent);
            comment.setBoard(null);
        } else {
            comment.setBoard(board);
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
}
