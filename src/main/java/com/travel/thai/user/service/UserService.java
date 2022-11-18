package com.travel.thai.user.service;

import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.domain.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {
    User signUp(UserDto userDto);
    boolean isExistUserId(String userId);
    boolean isExistUserName(String name);
    void modifyName(String userId, String name);
    void modfiyPassword(String userId, String password);
    void modifyEmail(String userId, String email);
    Page<BoardDto> searchBoardById(Search search);
}
