package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardCategory;
import com.travel.thai.bbs.domain.BoardType;

import java.util.List;

public interface BoardCategoryTypeRepositoryCustom {
    List<BoardType> getList(String categoryId);
    List<BoardType> getListForAdmin(String categoryId);
    String getBoardTypeName(String typeId, String categoryId);
    void unuseType(Long typeId);
    void useType(Long typeId);
    void modifyType(BoardType boardType);
    BoardType getType(Long typeId);
}
