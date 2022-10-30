package com.travel.thai.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private Long boardNum;
    private int pageNum;
    private int pageSize;
    private String keyword;
    private String content;

    private String type;
}
