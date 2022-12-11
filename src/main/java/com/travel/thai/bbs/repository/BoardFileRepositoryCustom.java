package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardFileDto;
import com.travel.thai.bbs.domain.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardFileRepositoryCustom {
    List<BoardFileDto> getImageList(Long boardId);
    PageImpl<BoardFileDto> getImageListForAdmin(Search search, Pageable pageable);
    void deleteImage(Search search);
    void restoreImage(Search search);

}
