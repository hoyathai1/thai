package com.travel.thai.bbs.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardCategory;
import com.travel.thai.bbs.repository.BoardCategoryRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoardCategory.boardCategory;

@RequiredArgsConstructor
public class BoardCategoryRepositoryImpl implements BoardCategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public BoardCategory getCategoryInfo(String categoryId) {
        BoardCategory result = queryFactory.select(boardCategory).from(boardCategory).where(boardCategory.id.eq(categoryId)).fetchOne();

        return result;
    }

    @Override
    public List<BoardCategory> getCategoryList() {
        List<BoardCategory> result = queryFactory
                .select(boardCategory)
                .from(boardCategory)
                .where(boardCategory.isUse.isTrue())
                .orderBy(boardCategory.orderBy.asc())
                .fetch();

        return result;
    }

    @Override
    public List<BoardCategory> getCategoryAllList() {
        List<BoardCategory> result = queryFactory
                .select(boardCategory)
                .from(boardCategory)
                .orderBy(boardCategory.orderBy.asc())
                .fetch();

        return result;
    }

    @Override
    public void unuseCategory(String categoryId) {
        queryFactory.update(boardCategory)
                .set(boardCategory.isUse, false)
                .where(boardCategory.id.eq(categoryId))
                .execute();
    }

    @Override
    public void useCategory(String categoryId) {
        queryFactory.update(boardCategory)
                .set(boardCategory.isUse, true)
                .where(boardCategory.id.eq(categoryId))
                .execute();
    }

    @Override
    public void modifyCategory(BoardCategory param) {
        queryFactory.update(boardCategory)
                .set(boardCategory.name, param.getName())
                .where(boardCategory.id.eq(param.getId()))
                .execute();
    }
}
