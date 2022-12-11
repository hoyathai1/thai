package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.BoardFileDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardFileRepositoryCustom;
import com.travel.thai.bbs.repository.BoardInformFileRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoardInform.boardInform;
import static com.travel.thai.bbs.domain.QBoardInformFile.boardInformFile;


@RequiredArgsConstructor
public class BoardInformFileRepositoryImpl implements BoardInformFileRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardFileDto> getImageList(Long boardId) {
        List<BoardFileDto> reesult = queryFactory.select(Projections.constructor(BoardFileDto.class,
                boardInformFile.fileName,
                boardInformFile.dir,
                boardInformFile.isDel
                ))
                .from(boardInformFile)
                .where(boardInformFile.boardId.eq(boardId))
                .fetch();

        return reesult;
    }

    @Override
    public PageImpl<BoardFileDto> getImageListForAdmin(Search search, Pageable pageable) {
        List<BoardFileDto> results = queryFactory
                .select(Projections.constructor(BoardFileDto.class,
                        boardInformFile.id,
                        boardInformFile.boardId,
                        boardInformFile.fileName,
                        boardInformFile.dir,
                        boardInform.title,
                        boardInformFile.isDel
                ))
                .from(boardInformFile)
                .leftJoin(boardInform).on(boardInform.id.eq(boardInformFile.boardId))
                .orderBy(boardInform.id.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory.selectOne().from(boardInformFile).fetch().size();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public void deleteImage(Search search) {
        queryFactory.update(boardInformFile)
                .set(boardInformFile.isDel, true)
                .where(boardInformFile.id.eq(search.getImageId()))
                .execute();
    }

    @Override
    public void restoreImage(Search search) {
        queryFactory.update(boardInformFile)
                .set(boardInformFile.isDel, false)
                .where(boardInformFile.id.eq(search.getImageId()))
                .execute();
    }
}
