package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardInform;
import com.travel.thai.bbs.domain.BoardInformDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardInformRepositoryCustom;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoard.board;
import static com.travel.thai.bbs.domain.QBoardCategory.boardCategory;
import static com.travel.thai.bbs.domain.QBoardInform.boardInform;
import static com.travel.thai.bbs.domain.QBoardType.boardType;
import static com.travel.thai.user.domain.QUser.user;

@RequiredArgsConstructor
public class BoardInformRepositoryImpl implements BoardInformRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardInformDto> search(Search search) {
        List<BoardInformDto> result = queryFactory
                .select(Projections.constructor(BoardInformDto.class,
                        boardInform.id,
                        boardInform.title,
                        user.name,
                        boardInform.createDate
                ))
                .from(boardInform)
                .leftJoin(user).on(boardInform.userId.eq(user.userId))
                .where(boardInform.category.eq(search.getCategory()).and(boardInform.type.eq(search.getType())).and(boardInform.isDel.isFalse()))
                .orderBy(boardInform.createDate.desc())
                .fetch();

        return result;
    }

    @Override
    public PageImpl<BoardInformDto> searchForAdmin(Search search, Pageable pageable) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(search.getContent())) {
            whereBuilder.and(boardInform.category.eq(search.getCategory()));
        }

        if (StringUtils.isNotEmpty(search.getType())) {
            whereBuilder.and(boardInform.type.eq(search.getType()));
        }

        List<BoardInformDto> result = queryFactory
                .select(Projections.constructor(BoardInformDto.class,
                        boardInform.id,
                        boardCategory.name,
                        boardType.name,
                        boardInform.title,
                        user.name,
                        boardInform.createDate,
                        boardInform.isDel
                ))
                .from(boardInform)
                .leftJoin(user).on(boardInform.userId.eq(user.userId))
                .leftJoin(boardCategory).on(boardCategory.id.eq(boardInform.category))
                .leftJoin(boardType).on(boardType.boardCategory.id.eq(boardCategory.id).and(boardType.type.eq(boardInform.type)))
                .where(whereBuilder)
                .orderBy(boardInform.createDate.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory.selectOne().from(boardInform).where(whereBuilder).fetch().size();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public BoardInformDto searchOne(Search search) {
        BoardInformDto result = queryFactory
                .select(Projections.constructor(BoardInformDto.class,
                        boardInform.id,
                        boardInform.title,
                        boardInform.contents,
                        user.name,
                        boardInform.createDate
                        ))
                .from(boardInform)
                .leftJoin(user).on(boardInform.userId.eq(user.userId))
                .where(boardInform.id.eq(search.getBoardNum()))
                .orderBy(boardInform.createDate.desc())
                .fetchOne();

        return result;
    }

    @Override
    public BoardInformDto searchOneForAdmin(Search search) {
        BoardInformDto result = queryFactory
                .select(Projections.constructor(BoardInformDto.class,
                        boardInform.id,
                        boardInform.category,
                        boardInform.type,
                        boardInform.title,
                        user.name,
                        boardInform.contents,
                        boardInform.createDate,
                        boardInform.isDel
                ))
                .from(boardInform)
                .leftJoin(user).on(boardInform.userId.eq(user.userId))
                .where(boardInform.id.eq(search.getBoardNum()))
                .orderBy(boardInform.createDate.desc())
                .fetchOne();

        return result;
    }

    @Override
    public void modifyBoardInform(BoardInform param) {
        queryFactory.update(boardInform)
                .set(boardInform.title, param.getTitle())
                .set(boardInform.contents, param.getContents())
                .set(boardInform.category, param.getCategory())
                .set(boardInform.type, param.getType())
                .where(boardInform.id.eq(param.getId()))
                .execute();
    }

    @Override
    public void deleteBoardInform(Search search) {
        queryFactory.update(boardInform)
                .set(boardInform.isDel, true)
                .where(boardInform.id.eq(search.getBoardNum()))
                .execute();
    }

    @Override
    public void restoreBoardInform(Search search) {
        queryFactory.update(boardInform)
                .set(boardInform.isDel, false)
                .where(boardInform.id.eq(search.getBoardNum()))
                .execute();
    }
}
