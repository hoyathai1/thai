package com.travel.thai.common.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Notice;
import com.travel.thai.common.domain.NoticeDto;
import com.travel.thai.common.repository.NoticeRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.travel.thai.bbs.domain.QBoardCategory.boardCategory;
import static com.travel.thai.common.domain.QNotice.notice;
import static com.travel.thai.user.domain.QUser.user;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<NoticeDto> search() {
        List<NoticeDto> result = queryFactory
                .select(Projections.constructor(NoticeDto.class,
                        notice.id,
                        boardCategory.name,
                        boardCategory.id,
                        notice.type,
                        notice.userId,
                        user.name,
                        notice.title,
                        notice.isDel,
                        notice.createDate,
                        notice.contents
                ))
                .from(notice)
                .leftJoin(user).on(user.userId.eq(notice.userId))
                .leftJoin(boardCategory).on(boardCategory.id.eq(notice.category))
                .where(notice.isDel.isFalse())
                .orderBy(notice.id.desc())
                .fetch();

        return result;
    }

    @Override
    public PageImpl<NoticeDto> searchForPaging(Search search, Pageable pageable) {
        List<NoticeDto> result = queryFactory
                .select(Projections.constructor(NoticeDto.class,
                        notice.id,
                        boardCategory.name,
                        boardCategory.id,
                        notice.type,
                        notice.userId,
                        user.name,
                        notice.title,
                        notice.isDel,
                        notice.createDate
                ))
                .from(notice)
                .leftJoin(user).on(user.userId.eq(notice.userId))
                .leftJoin(boardCategory).on(boardCategory.id.eq(notice.category))
                .where(notice.isDel.isFalse())
                .orderBy(notice.id.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(notice)
                .orderBy(notice.id.desc())
                .fetch().size();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public NoticeDto searchDetail(Search search) {
        NoticeDto dto = queryFactory
                .select(Projections.constructor(NoticeDto.class,
                        notice.id,
                        boardCategory.name,
                        boardCategory.id,
                        notice.type,
                        notice.userId,
                        user.name,
                        notice.title,
                        notice.isDel,
                        notice.createDate,
                        notice.contents
                ))
                .from(notice)
                .leftJoin(user).on(user.userId.eq(notice.userId))
                .leftJoin(boardCategory).on(boardCategory.id.eq(notice.category))
                .where(notice.id.eq(search.getBoardNotiId()).and(notice.isDel.isFalse()))
                .orderBy(notice.id.desc())
                .fetchOne();

        return dto;
    }

    @Override
    public PageImpl<NoticeDto> searchForPagingAdmin(Search search, Pageable pageable) {
        List<NoticeDto> result = queryFactory
                .select(Projections.constructor(NoticeDto.class,
                        notice.id,
                        boardCategory.name,
                        boardCategory.id,
                        notice.type,
                        notice.userId,
                        user.name,
                        notice.title,
                        notice.isDel,
                        notice.createDate,
                        notice.contents
                ))
                .from(notice)
                .leftJoin(user).on(user.userId.eq(notice.userId))
                .leftJoin(boardCategory).on(boardCategory.id.eq(notice.category))
                .orderBy(notice.id.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())    // 페이지 사이즈
                .fetch();

        int count = queryFactory
                .selectOne()
                .from(notice)
                .orderBy(notice.id.desc())
                .fetch().size();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public void updateNotice(Notice param) {
        queryFactory.update(notice)
                .set(notice.title, param.getTitle())
                .set(notice.contents, param.getContents())
                .set(notice.contentsTxt, param.getContentsTxt())
                .set(notice.author, param.getAuthor())
                .set(notice.userId, param.getUserId())
                .set(notice.type, param.getType())
                .where(notice.id.eq(param.getId()))
                .execute();
    }

    @Override
    public void activeNotice(Notice param) {
        queryFactory.update(notice)
                .set(notice.isDel, param.isDel())
                .where(notice.id.eq(param.getId()))
                .execute();
    }
}
