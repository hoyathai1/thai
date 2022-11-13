package com.travel.thai.user.domain;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String password;
    private String email;
    private String name;
    private String ip;
}
