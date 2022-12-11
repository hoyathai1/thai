package com.travel.thai.bbs.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class BoardFileDto {
    private Long id;
    private Long boardId;
    private String name;
    private String dir;
    private String title;
    private boolean isDel;

    @Builder
    public BoardFileDto(String name, String dir, boolean isDel) {
        this.name = name;
        this.dir = dir;
        this.isDel = isDel;
    }

    @Builder
    public BoardFileDto(Long id, Long boardId, String name, String dir, String title, boolean isDel) {
        this.id = id;
        this.boardId = boardId;
        this.name = name;
        this.dir = dir;
        this.title = title;
        this.isDel = isDel;
    }
}
