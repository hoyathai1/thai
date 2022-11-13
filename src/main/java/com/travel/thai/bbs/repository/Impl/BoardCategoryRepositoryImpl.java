package com.travel.thai.bbs.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardCategory;
import com.travel.thai.bbs.repository.BoardCategoryRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.travel.thai.bbs.domain.QBoardCategory.boardCategory;

@RequiredArgsConstructor
public class BoardCategoryRepositoryImpl implements BoardCategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public BoardCategory getCategoryInfo(String categoryId) {
        BoardCategory result = queryFactory.select(boardCategory).from(boardCategory).where(boardCategory.id.eq(categoryId)).fetchOne();

        return result;
    }
}
