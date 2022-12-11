package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardCategory;
import com.travel.thai.bbs.domain.BoardCategoryDto;

import java.util.List;

public interface BoardCategoryRepositoryCustom {
    BoardCategory getCategoryInfo(String categoryId);
    List<BoardCategory> getCategoryList();
    List<BoardCategory> getCategoryAllList();
    void unuseCategory(String categoryId);
    void useCategory(String categoryId);
    void modifyCategory(BoardCategory boardCategory);
}
