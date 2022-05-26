package com.example.shonicserver.service;

import com.example.shonicserver.dto.UserDto;

public interface UserService {
   public UserDto create(UserDto userDto) throws Exception;
}
