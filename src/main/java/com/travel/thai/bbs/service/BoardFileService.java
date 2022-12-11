package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.BoardFileDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.Page;

public interface BoardFileService {
    Page<BoardFileDto> getImageListForAdmin(Search search);
    void deleteImage(Search search);
    void restoreImage(Search search);
}
