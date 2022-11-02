package com.travel.thai.bbs.domain;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {
    private String author;
    private String password;
    private String content;
    private Long boardId;
    private Long parentId;
    private String ip;

    private Page<Comment> list;

    private int commentTotalCount;

    public CommentDto() {
    }

    public CommentDto(Page<Comment> list, int commentTotalCount) {
        this.list = list;
        this.commentTotalCount = commentTotalCount;
    }
}
