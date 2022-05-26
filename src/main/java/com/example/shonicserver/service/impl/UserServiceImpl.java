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
        if(userDto.getRoles().equalsIgnoreCase("ROLE_ADMIN")) {
            name = ERole.ROLE_ADMIN;
        }else if(userDto.getRoles().equalsIgnoreCase("ROLE_CUSTOMER")) {
            name = ERole.ROLE_CUSTOMER;
        }else {
            throw new Exception(userDto.getRoles() + " is not a valid role");
        }
        return userDto;
    }

}
