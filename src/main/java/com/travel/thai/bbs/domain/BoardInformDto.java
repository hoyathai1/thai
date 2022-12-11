package com.travel.thai.bbs.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class BoardInformDto {
    private Long id;
    private String category;
    private String type;
    private String title;
    private String userId;
    private String username;
    private String contents;
    private String contentsTxt;
    private LocalDateTime createDate;
    private int view;
    private boolean isDel;

    @Builder
    public BoardInformDto(Long id, String title, String username, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.createDate = createDate;
    }

    @Builder
    public BoardInformDto(Long id, String category, String type, String title, String username, LocalDateTime createDate, boolean isDel) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.title = title;
        this.username = username;
        this.createDate = createDate;
        this.isDel = isDel;
    }

    @Builder
    public BoardInformDto(Long id, String title, String contents, String username, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.contents = contents;
        this.createDate = createDate;
    }

    @Builder
    public BoardInformDto(Long id, String category, String type, String title, String username, String contents, LocalDateTime createDate, boolean isDel) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.title = title;
        this.username = username;
        this.contents = contents;
        this.createDate = createDate;
        this.isDel = isDel;
    }
}
