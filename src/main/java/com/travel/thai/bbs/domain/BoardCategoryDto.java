package com.travel.thai.bbs.domain;

import lombok.Data;

import java.util.List;

@Data
public class BoardCategoryDto {
    private String id;
    private String name;
    private List<BoardType> types;

    public BoardCategoryDto(String id, String name, List<BoardType> types) {
        this.id = id;
        this.name = name;
        this.types = types;
    }
}
