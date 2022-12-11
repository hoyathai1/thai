package com.travel.thai.user.service.Impl;

import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.BoardFileDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.common.util.StringUtils;
import com.travel.thai.user.domain.User;
import com.travel.thai.user.domain.UserDto;
import com.travel.thai.user.repository.UserRepository;
import com.travel.thai.user.service.UserDetailService;
import com.travel.thai.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Transactional
    @Override
    public User signUp(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setIp(userDto.getIp());

        if (StringUtils.isNotEmpty(userDto.getAuth())) {
            user.setUserAuth(userDto.getAuth());
        } else {
            user.setUserAuth("ROLE_USER");
        }

        return userRepository.save(user);
    }

    @Override
    public boolean isExistUserId(String userId) {
        return userRepository.isExistUserId(userId);
    }

    @Override
    public boolean isExistUserName(String name) {
        return userRepository.isExistUserName(name);
    }

    @Override
    @Transactional
    public void modifyName(String userId, String name) {
        userRepository.modifyName(userId, name);
        refreshLoginAuthInfo();
    }

    @Override
    @Transactional
    public void modfiyPassword(String userId, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode(password);
        userRepository.modifyPassword(userId, encPassword);

        refreshLoginAuthInfo();
    }

    @Override
    @Transactional
    public void modfiyPasswordForAdmin(String userId, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode(password);
        userRepository.modifyPassword(userId, encPassword);

    }

    @Override
    @Transactional
    public void modifyEmail(String userId, String email) {
        userRepository.modifyEmail(userId, email);
        refreshLoginAuthInfo();
    }

    @Override
    @Transactional
    public void modifyEmailForAdmin(String userId, String email) {
        userRepository.modifyEmail(userId, email);
    }


    protected void refreshLoginAuthInfo () {
        // 2. 현재 Authentication에 저장된 account의 age값 변경
        // 2-1. 현재 Authentication 정보 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // 2-2. 현재 Authentication로 사용자 인증 후 새 Authentication 정보를 SecurityContextHolder에 세팅
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, user.getUserId()));
    }

    /**
     * @description 새로운 인증 생성
     * @param currentAuth 현재 auth 정보
     * @param username	현재 사용자 Id
     * @return Authentication
     * @author Armton
     */
    protected Authentication createNewAuthentication(Authentication currentAuth, String username) {
        UserDetails newPrincipal = userDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(newPrincipal, currentAuth.getCredentials(), newPrincipal.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }

    @Override
    public Page<BoardDto> searchBoardById(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), search.getPageSize()
        );

        return boardRepository.searchById(search, pageable);
    }

    @Override
    public Page<UserDto> search(Search search) {
        Pageable pageable = PageRequest.of(
                search.getPageNum(), 10
        );

        return userRepository.search(search, pageable);
    }

    @Override
    @Transactional
    public void deleteUser(Search search) {
        userRepository.deleteUser(search);
    }

    @Override
    @Transactional
    public void restoreUser(Search search) {
        userRepository.restoreUser(search);
    }
}
