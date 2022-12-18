package com.travel.thai.bbs.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookMarkDto {
    private Long bookmarkId;
    private Long boardId;
    private String title;
    private String author;
    private String name;
    private LocalDateTime createDate;
    private String category;
    private String type;
    private int view;
    private int likes;
    private int comment;

    private String typeName;

    @Builder
    public BookMarkDto(Long bookmarkId, Long boardId, String title, String author, LocalDateTime createDate, String category) {
        this.bookmarkId = bookmarkId;
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.category = category;
    }

    @Builder
    public BookMarkDto(Long bookmarkId, Long boardId, String title, String author, String name, LocalDateTime createDate, String category, String type, int view, int likes, int comment) {
        this.bookmarkId = bookmarkId;
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.name = name;
        this.createDate = createDate;
        this.category = category;
        this.type = type;
        this.view = view;
        this.likes = likes;
        this.comment = comment;
    }
}
