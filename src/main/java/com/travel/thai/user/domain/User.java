package com.travel.thai.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, unique = true, nullable = false)
    private String userId;

    private String password;


    private String userAuth;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String ip;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @ColumnDefault(value = "false")
    private boolean isDel;

//    @Transient
//    private List<GrantedAuthority> authorities;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createDate = LocalDateTime.now();
        this.updateDate = this.createDate;
    }

    @PreUpdate // 데이터 수정이 이루어질때 사전 작업
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }


    /**
     * 시큐리티
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    // 시큐리티의 userName
    // -> 따라서 얘는 인증할 때 id를 봄
    @Override
    public String getUsername() {
        return this.userId;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(this.userAuth));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.getUserAuth()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public User(String userId, String password, String userAuth, String email, String name, String ip) {
        this.userId = userId;
        this.password = password;
        this.userAuth = userAuth;
        this.email = email;
        this.name = name;
        this.ip = ip;
    }
}
