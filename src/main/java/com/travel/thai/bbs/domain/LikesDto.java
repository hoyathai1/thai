package com.travel.thai.bbs.domain;

import lombok.Data;

@Data
public class LikesDto {
    private Long boardId;
    private String ip;
    private String userId;
}
