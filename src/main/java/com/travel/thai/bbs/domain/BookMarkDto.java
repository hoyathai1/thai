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
    private LocalDateTime createDate;
    private String category;

    @Builder
    public BookMarkDto(Long bookmarkId, Long boardId, String title, String author, LocalDateTime createDate, String category) {
        this.bookmarkId = bookmarkId;
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.category = category;
    }
}
