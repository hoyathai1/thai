package com.travel.thai.user.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.repository.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.travel.thai.user.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public User searchOne(String userId) {
        User result = queryFactory
                .select(user)
                .from(user)
                .where(user.userId.eq(userId).and(user.isDel.isFalse()))
                .fetchOne();

        return result;
    }

    @Override
    public User searchOne(String userId, String email) {
        User result = queryFactory
                .select(user)
                .from(user)
                .where(user.userId.eq(userId).and(user.email.eq(email)).and(user.isDel.isFalse()))
                .fetchOne();

        return result;
    }

    @Override
    public void modifyPassword(String userId, String password) {
        queryFactory.update(user).set(user.password, password).where(user.userId.eq(userId)).execute();
    }

    @Override
    public boolean isExistUserId(String userId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(user)
                .where(user.userId.eq(userId))
                .fetchFirst(); // limit 1

        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }

    @Override
    public boolean isExistUserName(String name) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(user)
                .where(user.name.eq(name))
                .fetchFirst(); // limit 1

        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }

    @Override
    public void modifyName(String userId, String name) {
        queryFactory.update(user).set(user.name, name).where(user.userId.eq(userId)).execute();
    }

    @Override
    public void modifyEmail(String userId, String email) {
        queryFactory.update(user).set(user.email, email).where(user.userId.eq(userId)).execute();
    }
}
