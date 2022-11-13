package com.travel.thai.user.service;

import com.travel.thai.user.domain.User;
import com.travel.thai.user.domain.UserDto;

public interface UserService {
    User signUp(UserDto userDto);
    boolean isExistUserId(String userId);
    boolean isExistUserName(String name);
}
