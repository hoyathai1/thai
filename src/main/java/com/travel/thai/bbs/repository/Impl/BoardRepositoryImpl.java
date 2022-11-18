package com.travel.thai.bbs.repository.Impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepositoryCustom;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoard.board;
import static com.travel.thai.bbs.domain.QComment.comment;
import static com.travel.thai.bbs.domain.QBookMark.bookMark;
import static com.travel.thai.user.domain.QUser.user;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private static final String ALL = "all";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    private static final String BEST = "Y";

    private static final String TYPE_BOARD = "board";
    private static final String TYPE_INFO = "info";
    private static final String TYPE_FUN = "fun";
    private static final String TYPE_FIND = "find";     // 이푸알


    private static final int BEST_SIZE = 10;

    @Override
    public PageImpl<BoardDto> search(Search search, Pageable pageable) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        // 검색어 있을때
        if (StringUtils.isNotEmpty(search.getContent())) {
            if (ALL.equals(search.getKeyword()) & StringUtils.isNotEmpty(search.getContent())) {
                whereBuilder.and(board.title.contains(search.getContent()))
                        .or(board.contentsTxt.contains(search.getContent()));
            } else if (TITLE.equals(search.getKeyword())) {
                whereBuilder.and(board.title.contains(search.getContent()));
            } else if (CONTENT.equals(search.getKeyword())) {
                whereBuilder.and(board.contentsTxt.contains(search.getContent()));
            }
        }

        if (BEST.equals(search.getBest())) {
            whereBuilder.and(board.likes.size().gt(BEST_SIZE));
        } else {
            if (!ALL.equals(search.getType())) {
                whereBuilder.and(board.type.eq(search.getType()));
            }
        }

        List<BoardDto> results = queryFactory
                .select(Projections.constructor(BoardDto.class,
                        board.id,
                        board.title,
                        board.author,
                        board.createDate,
                        board.view,
                        comment.id.count().intValue(),
                        board.likes.size(),
                        board.isUser,
                        board.ip
                ))
                .from(board)
                .leftJoin(comment).on(board.id.eq(comment.upper).and(comment.isDel.isFalse()))
                .where(whereBuilder.and(board.category.eq(search.getCategory())).and(board.isDel.isFalse()))
                .groupBy(board.id)
                .orderBy(board.createDate.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(board)
                .where(whereBuilder.and(board.category.eq(search.getCategory())).and(board.isDel.isFalse()))
                .orderBy(board.createDate.desc())
                .fetch().size();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public PageImpl<BoardDto> searchById(Search search, Pageable pageable) {
        List<BoardDto> results = queryFactory
                .select(Projections.constructor(BoardDto.class,
                        board.id,
                        board.category,
                        board.title,
                        board.contents,
                        board.createDate,
                        board.likes.size(),
                        board.view,
                        comment.id.count().intValue()
                ))
                .from(board)
                .leftJoin(comment).on(board.id.eq(comment.upper).and(comment.isDel.isFalse()))
                .leftJoin(user).on(board.userId.eq(user.userId))
                .where(board.isDel.isFalse().and(board.userId.eq(search.getUserId())))
                .groupBy(board.id)
                .orderBy(board.createDate.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(board)
                .where(board.isDel.isFalse().and(board.userId.eq(search.getUserId())))
                .orderBy(board.createDate.desc())
                .fetch().size();

        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public BoardDto searchOne(Search search) {
        BoardDto result = queryFactory
                .select(Projections.constructor(BoardDto.class,
                        board.id,
                        board.title,
                        board.author,
                        board.contents,
                        board.createDate,
                        board.view,
                        board.likes.size(),
                        board.isUser,
                        board.userId,
                        board.ip,
                        board.type
                ))
                .from(board)
                .where(board.id.eq(search.getBoardNum()).and(board.isDel.isFalse()))
                .fetchOne();

        return result;
    }

    @Override
    public void increseViewCount(Search search) {
        queryFactory
                .update(board)
                .set(board.view, board.view.add(1))
                .where(board.id.eq(search.getBoardNum()))
                .execute();
    }

    @Override
    public boolean isExistBoard(long boardNum) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(board)
                .where(board.id.eq(boardNum))
                .fetchFirst(); // limit 1

        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }

    @Override
    public Board existBoard(long boardNum) {
        Board result = queryFactory.select(board).from(board)
        .where(board.id.eq(boardNum))
        .fetchFirst();

        return result;
    }

    @Override
    public void deleteBoard(long boardNum) {
        queryFactory
                .update(board)
                .set(board.isDel, true)
                .where(board.id.eq(boardNum))
                .execute();
    }

    @Override
    public String getPassword(long boardNum) {
        String result = queryFactory.select(board.password)
                .from(board)
                .where(board.id.eq(boardNum))
                .fetchOne();

        return result;
    }

    @Override
    public void modifyBoard(Board param) {
        queryFactory.update(board)
                .set(board.title, param.getTitle())
                .set(board.contents, param.getContents())
                .set(board.contentsTxt, param.getContentsTxt())
                .set(board.type, param.getType())
                .where(board.id.eq(param.getId()))
                .execute();
    }
}
