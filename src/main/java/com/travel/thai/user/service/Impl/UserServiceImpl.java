package com.travel.thai.user.service.Impl;

import com.travel.thai.bbs.domain.BoardDto;
import com.travel.thai.bbs.domain.BoardFileDto;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.bbs.repository.BoardRepository;
import com.travel.thai.common.domain.DayStat;
import com.travel.thai.common.service.DayStatService;
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

    @Autowired
    private DayStatService dayStatService;

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

        User register = userRepository.save(user);
        dayStatService.statNewUser();

        return register;
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
        // 2. ?????? Authentication??? ????????? account??? age??? ??????
        // 2-1. ?????? Authentication ?????? ??????
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // 2-2. ?????? Authentication??? ????????? ?????? ??? ??? Authentication ????????? SecurityContextHolder??? ??????
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, user.getUserId()));
    }

    /**
     * @description ????????? ?????? ??????
     * @param currentAuth ?????? auth ??????
     * @param username	?????? ????????? Id
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

    @Override
    @Transactional
    public void modifyUserInfo(UserDto userDto) {
        userRepository.modifyUserInfo(userDto);

        refreshLoginAuthInfo();
    }
}
