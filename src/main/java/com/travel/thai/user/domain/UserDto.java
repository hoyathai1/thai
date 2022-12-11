package com.travel.thai.user.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String userId;
    private String password;
    private String email;
    private String name;
    private String ip;
    private String auth;
    private LocalDateTime createDate;
    private boolean isDel;

    public UserDto(String userId, String email, String name, String ip, String auth, LocalDateTime createDate, boolean isDel) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.ip = ip;
        this.auth = auth;
        this.createDate = createDate;
        this.isDel = isDel;
    }
}
