package com.travel.thai.user.service.Impl;

import com.travel.thai.user.domain.User;
import com.travel.thai.user.domain.UserDto;
import com.travel.thai.user.repository.UserRepository;
import com.travel.thai.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
        user.setUserAuth("USER");

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
}
