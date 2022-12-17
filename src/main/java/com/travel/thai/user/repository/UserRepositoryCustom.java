package com.travel.thai.user.repository;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.domain.UserDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    User searchOne(String userId);
    User searchOne(String userId, String email);
    void modifyPassword(String userId, String password);
    boolean isExistUserId(String userId);
    boolean isExistUserName(String name);

    void modifyName(String userId, String name);
    void modifyEmail(String userId, String email);

    PageImpl<UserDto> search(Search search, Pageable pageable);
    void deleteUser(Search search);

    void restoreUser(Search search);
    void modifyUserInfo(UserDto userDto);
}
