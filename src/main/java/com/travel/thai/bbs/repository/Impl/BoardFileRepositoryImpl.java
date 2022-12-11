package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.BoardFileDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardFileRepositoryCustom;
import com.travel.thai.bbs.repository.BoardRepositoryCustom;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoard.board;
import static com.travel.thai.bbs.domain.QBoardFile.boardFile;


@RequiredArgsConstructor
public class BoardFileRepositoryImpl implements BoardFileRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardFileDto> getImageList(Long boardId) {
        List<BoardFileDto> reesult = queryFactory.select(Projections.constructor(BoardFileDto.class,
                boardFile.fileName,
                boardFile.dir,
                boardFile.isDel
                ))
                .from(boardFile)
                .where(boardFile.boardId.eq(boardId))
                .fetch();

        return reesult;
    }

    @Override
    public PageImpl<BoardFileDto> getImageListForAdmin(Search search, Pageable pageable) {
        List<BoardFileDto> results = queryFactory
                .select(Projections.constructor(BoardFileDto.class,
                        boardFile.id,
                        boardFile.boardId,
                        boardFile.fileName,
                        boardFile.dir,
                        board.title,
                        boardFile.isDel
                ))
                .from(boardFile)
                .leftJoin(board).on(board.id.eq(boardFile.boardId))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory.selectOne().from(boardFile).fetch().size();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public void deleteImage(Search search) {
        queryFactory.update(boardFile)
                .set(boardFile.isDel, true)
                .where(boardFile.id.eq(search.getImageId()))
                .execute();
    }

    @Override
    public void restoreImage(Search search) {
        queryFactory.update(boardFile)
                .set(boardFile.isDel, false)
                .where(boardFile.id.eq(search.getImageId()))
                .execute();
    }
}
