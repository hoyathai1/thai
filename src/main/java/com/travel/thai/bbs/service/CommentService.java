package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment saveComment(CommentDto commentDto);
    CommentDto search(Search search);
}
