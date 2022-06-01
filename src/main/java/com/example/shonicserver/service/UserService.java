package com.example.shonicserver.service;

import com.example.shonicserver.dto.RegisterDto;
import com.example.shonicserver.dto.UserDto;
import com.example.shonicserver.payload.response.UserResponse;

public interface UserService {
   public UserResponse create(RegisterDto registerDto) throws Exception;
}
