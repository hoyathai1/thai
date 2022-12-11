package com.travel.thai.common.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {
    private Long id;
    private String categoryName;
    private String categoryId;
    private String type;
    private String userId;
    private String author;
    private String title;
    private boolean isDel;
    private LocalDateTime createDate;
    private String contents;

    public NoticeDto(Long id, String categoryName, String categoryId, String type, String userId, String author, String title, boolean isDel, LocalDateTime createDate, String contents) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.type = type;
        this.userId = userId;
        this.author = author;
        this.title = title;
        this.isDel = isDel;
        this.createDate = createDate;
        this.contents = contents;
    }
}
