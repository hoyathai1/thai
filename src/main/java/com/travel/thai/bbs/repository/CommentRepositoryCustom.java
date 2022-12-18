package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.Comment;
import com.travel.thai.bbs.domain.CommentDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {
    CommentDto search(Search search, Pageable pageable);
    String searchForUserId(Long id);
    String searchPassword(Search search);
    void deleteComment(Long id);

    PageImpl<CommentDto> searchForAdmin(Search search, Pageable pageable);
    void deleteComment(Search search);
    void restoreComment(Search search);

    void modifyContent(CommentDto dto);

    CommentDto searchListForAdmin(Search search, Pageable pageable);
    PageImpl<CommentDto> searchListForPc(Search search, Pageable pageable);
}
