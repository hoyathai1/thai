package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardType;

import java.util.List;

public interface BoardCategoryTypeRepositoryCustom {
    List<BoardType> getList(String categoryId);
    String getBoardTypeName(String typeId);
}
