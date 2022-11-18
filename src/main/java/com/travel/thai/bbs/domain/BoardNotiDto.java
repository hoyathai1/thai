package com.travel.thai.bbs.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class BoardNotiDto {
    private Long id;
    private Long boardId;
    private Long commentId;
    private String title;
    private String content;
    private String author;
    private String ip;
    private Boolean isUser;
    private LocalDateTime createDate;
    private String category;

    @Builder
    public BoardNotiDto(Long id, Long boardId, Long commentId, String title, String content, String author, String ip, Boolean isUser, LocalDateTime createDate, String category) {
        this.id = id;
        this.boardId = boardId;
        this.commentId = commentId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.ip = ip;
        this.isUser = isUser;
        this.createDate = createDate;
        this.category = category;
    }

    public String getIp() {
        if (isUser) {
            return "";
        } else {
            String[] arr = ip.split("\\.");

            return arr[0] + "." +arr[1];
        }
    }
}
