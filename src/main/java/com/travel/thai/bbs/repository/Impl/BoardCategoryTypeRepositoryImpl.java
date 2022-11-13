package com.travel.thai.bbs.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardType;
import com.travel.thai.bbs.repository.BoardCategoryTypeRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoardType.boardType;

@RequiredArgsConstructor
public class BoardCategoryTypeRepositoryImpl implements BoardCategoryTypeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardType> getList(String categoryId) {
        List<BoardType> result = queryFactory
                .select(boardType)
                .from(boardType)
                .where(boardType.categoryId.eq(categoryId))
                .orderBy(boardType.orderBy.asc())
                .fetch();

        return result;
    }

    @Override
    public String getBoardTypeName(String typeId) {
        String result = queryFactory.select(boardType.name).from(boardType).where(boardType.type.eq(typeId)).fetchOne();
        return result;
    }
}
