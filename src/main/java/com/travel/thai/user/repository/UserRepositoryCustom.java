package com.travel.thai.user.repository;

import com.travel.thai.user.domain.User;

public interface UserRepositoryCustom {
    User searchOne(String userId);
    User searchOne(String userId, String email);
    void modifyPassword(String userId, String password);
    boolean isExistUserId(String userId);
    boolean isExistUserName(String name);

    void modifyName(String userId, String name);
    void modifyEmail(String userId, String email);
}
