package com.travel.thai.bbs.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDto {
    private Long id;
    private String type;
    private String title;
    private String contents;
    private String contentsTxt;
    private String author;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private int view;

    //private List<Comment> comments;
    private int commentCount;

    @Builder
    public BoardDto(Long id, String title, String author, LocalDateTime createDate, int view, int commentCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.view = view;
        this.commentCount = commentCount;
    }
}
