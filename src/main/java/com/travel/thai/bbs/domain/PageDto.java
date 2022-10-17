package com.travel.thai.bbs.domain;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageDto {
    //private int totalCount;
    private int curPage;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    private int displayPageNum = 5;     // page 개수

    public PageDto() {
    }

    public PageDto(long total, Pageable pageable) {
        this.curPage = pageable.getPageNumber()+1;      // page 시작이 0부터라서
        endPage = (int) ( Math.ceil((double) curPage / displayPageNum) * displayPageNum );
        startPage = (endPage - displayPageNum);

        int tempEndPage = (int) ( Math.ceil((double) total / pageable.getPageSize()) );

        if(endPage > tempEndPage) {
            endPage = tempEndPage;
        }

        endPage = endPage-1;    // page 시작이 0부터라서
        curPage = curPage-1;

        prev = startPage == 0 ? false : true;
        next = endPage * pageable.getPageSize() >= total ? false : true;
    }
}
