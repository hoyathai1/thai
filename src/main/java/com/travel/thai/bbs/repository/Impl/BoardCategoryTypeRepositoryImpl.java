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
                .where(boardType.boardCategory.id.eq(categoryId).and(boardType.isUse.isTrue()))
                .orderBy(boardType.orderBy.asc())
                .fetch();

        return result;
    }

    @Override
    public List<BoardType> getListForAdmin(String categoryId) {
        List<BoardType> result = queryFactory
                .select(boardType)
                .from(boardType)
                .where(boardType.boardCategory.id.eq(categoryId))
                .orderBy(boardType.orderBy.asc())
                .fetch();

        return result;
    }

    @Override
    public String getBoardTypeName(String typeId, String categoryId) {
        String result = queryFactory.select(boardType.name).from(boardType).where(boardType.type.eq(typeId).and(boardType.boardCategory.id.eq(categoryId))).fetchOne();
        return result;
    }

    @Override
    public void unuseType(Long typeId) {
        queryFactory
                .update(boardType)
                .set(boardType.isUse, false)
                .where(boardType.id.eq(typeId))
                .execute();
    }

    @Override
    public void useType(Long typeId) {
        queryFactory
                .update(boardType)
                .set(boardType.isUse, true)
                .where(boardType.id.eq(typeId))
                .execute();
    }

    @Override
    public void modifyType(BoardType param) {
        queryFactory
                .update(boardType)
                .set(boardType.name, param.getName())
                .set(boardType.type, param.getType())
                .set(boardType.orderBy, param.getOrderBy())
                .where(boardType.id.eq(param.getId()))
                .execute();
    }

    @Override
    public BoardType getType(Long typeId) {
        BoardType result = queryFactory.select(boardType).from(boardType).where(boardType.id.eq(typeId)).fetchOne();

        return result;
    }
}
