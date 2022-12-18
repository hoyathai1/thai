package com.travel.thai.bbs.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {
    private Long id;
    private String author;
    private String password;
    private String content;
    private Long boardId;
    private Long parentId;
    private String ip;
    private String userId;
    private String username;
    private boolean isUser;
    private boolean isDel;

    private Page<Comment> list;

    private int commentTotalCount;

    private LocalDateTime createDate;

    private String title;
    private String category;

    public CommentDto() {
    }

    @Builder
    public CommentDto(Page<Comment> list, int commentTotalCount) {
        this.list = list;
        this.commentTotalCount = commentTotalCount;
    }

    @Builder
    public CommentDto(Long id, String author, String content, Long boardId, Long parentId, String ip, String username, boolean isUser, boolean isDel, int commentTotalCount, LocalDateTime createDate) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.boardId = boardId;
        this.parentId = parentId;
        this.ip = ip;
        this.username = username;
        this.isUser = isUser;
        this.isDel = isDel;
        this.commentTotalCount = commentTotalCount;
        this.createDate = createDate;
    }

    @Builder
    public CommentDto(Long id, String content, Long boardId, LocalDateTime createDate, String title, String category) {
        this.id = id;
        this.content = content;
        this.boardId = boardId;
        this.createDate = createDate;
        this.title = title;
        this.category = category;
    }
}
