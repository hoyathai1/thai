package com.travel.thai.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private String userId;
    private Long imageId;
    private String userName;
    private String email;
    private Long boardNum;
    private Long commentNum;
    private int pageNum;
    private int pageSize;
    private String keyword;
    private String content;
    private String role;

    private String category;   // 게시판 종류
    private String type;   // 해당개시판 종류

    private String best;

    private String password;
}
