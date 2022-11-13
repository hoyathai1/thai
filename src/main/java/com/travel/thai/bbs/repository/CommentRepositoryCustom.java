package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    CommentDto search(Search search, Pageable pageable);
    String searchForUserId(Long id);
    String searchPassword(Search search);
    void deleteComment(Long id);
}
