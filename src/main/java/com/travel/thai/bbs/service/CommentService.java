package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;

public interface CommentService {
    Comment saveComment(CommentDto commentDto);
    CommentDto search(Search search);
    boolean deleteComment(Search search);
}
