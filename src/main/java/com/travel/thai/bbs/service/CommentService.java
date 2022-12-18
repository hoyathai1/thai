package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.user.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    Comment saveComment(CommentDto commentDto, User user);
    CommentDto search(Search search);
    boolean deleteComment(Search search, User user);
    Page<CommentDto> searchForAdmin(Search search);

    void deleteComment(Search search);
    void restoreComment(Search search);
    void modifyContent(CommentDto dto);

    CommentDto searchListForAdmin(Search search);

    Page<CommentDto> searchListForPc(Search search);
}
