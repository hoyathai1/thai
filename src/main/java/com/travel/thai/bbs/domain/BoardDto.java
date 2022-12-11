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
    private String username;
    private String author;
    private String password;
    private String category;
    private String categoryName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private int likes;
    private boolean isUser;
    private String userId;
    private String ip;

    private int view;

    //private List<Comment> comments;
    private int commentCount;

    private boolean isDel;

    @Builder
    public BoardDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Builder
    public BoardDto(Long id, String title, String username, String author, String contents, LocalDateTime createDate, int view, int likes, boolean isUser, String userId, String ip, String type, String category) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.author = author;
        this.createDate = createDate;
        this.likes = likes;
        this.view = view;
        this.isUser = isUser;
        this.userId = userId;
        this.ip = ip;
        this.type = type;
        this.category = category;
    }

    @Builder
    public BoardDto(Long id, String title, String username, String author, LocalDateTime createDate, int view, int commentCount, int likes, boolean isUser, String ip, String category, String categoryName, String type) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.author = author;
        this.createDate = createDate;
        this.view = view;
        this.commentCount = commentCount;
        this.likes = likes;
        this.isUser = isUser;
        this.ip = ip;
        this.category = category;
        this.categoryName = categoryName;
        this.type = type;

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

    @Builder
    public BoardDto(Long id, String type, String title, String username, String author, String category, String categoryName, LocalDateTime createDate, int likes, boolean isUser, String ip, int view, int commentCount, boolean isDel) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.username = username;
        this.author = author;
        this.category = category;
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.likes = likes;
        this.isUser = isUser;
        this.ip = ip;
        this.view = view;
        this.commentCount = commentCount;
        this.isDel = isDel;
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

