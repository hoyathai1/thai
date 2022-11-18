package com.travel.thai.bbs.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {
    private Long id;
    private String type;
    private String typeName;
    private String title;
    private String contents;
    private String contentsTxt;
    private String author;
    private String password;
    private String category;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private int likes;
    private boolean isUser;
    private String userId;
    private String ip;

    private int view;

    //private List<Comment> comments;
    private int commentCount;

    @Builder
    public BoardDto(Long id, String title, String author, String contents, LocalDateTime createDate, int view, int likes, boolean isUser, String userId, String ip, String type) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.createDate = createDate;
        this.likes = likes;
        this.view = view;
        this.isUser = isUser;
        this.userId = userId;
        this.ip = ip;
        this.type = type;
    }

    @Builder
    public BoardDto(Long id, String title, String author, LocalDateTime createDate, int view, int commentCount, int likes, boolean isUser, String ip) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.view = view;
        this.commentCount = commentCount;
        this.likes = likes;
        this.isUser = isUser;
        this.ip = ip;
    }

    /**
     * 내가쓴글에서 사용
     *
     * @param id
     * @param title
     * @param contents
     * @param createDate
     * @param likes
     * @param view
     * @param commentCount
     */
    @Builder
    public BoardDto(Long id, String category, String title, String contents, LocalDateTime createDate, int likes, int view, int commentCount) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.likes = likes;
        this.view = view;
        this.commentCount = commentCount;
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
