package com.travel.thai.bbs.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, unique = true, nullable = false)
    private String userId;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String signUpDate;  // 가입날짜

    @Column
    private String ip;

}
