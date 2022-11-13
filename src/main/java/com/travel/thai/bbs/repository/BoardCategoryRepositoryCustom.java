package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardCategory;

public interface BoardCategoryRepositoryCustom {
    BoardCategory getCategoryInfo(String categoryId);
}
