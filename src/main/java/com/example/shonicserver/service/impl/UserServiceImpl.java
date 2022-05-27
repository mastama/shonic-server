package com.example.shonicserver.service.impl;

import com.example.shonicserver.dto.UserDto;
import com.example.shonicserver.model.ERole;
import com.example.shonicserver.repository.UserRepository;
import com.example.shonicserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) throws Exception {
        ERole name;

            name = ERole.ROLE_CUSTOMER;

        return userDto;
    }

}
