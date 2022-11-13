package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.user.domain.User;

public interface CommentService {
    Comment saveComment(CommentDto commentDto, User user);
    CommentDto search(Search search);
    boolean deleteComment(Search search, User user);
}
