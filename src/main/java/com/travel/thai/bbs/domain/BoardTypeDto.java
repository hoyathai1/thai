package com.travel.thai.bbs.domain;

import lombok.Data;

@Data
public class BoardTypeDto {
    private Long id;

    private String type;

    private String name;

    private String categoryId;

    private int orderBy;

    private boolean isUse;

}
