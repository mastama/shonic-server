package com.example.shonicserver.service;

import com.example.shonicserver.dto.RegisterDto;
import com.example.shonicserver.dto.UserDto;
import com.example.shonicserver.model.User;
import com.example.shonicserver.payload.response.UserResponse;

import java.util.Optional;

public interface UserService {
   public UserResponse create(RegisterDto registerDto) throws Exception;
   public Optional<User> findByEmail(String email);
   public void makeAsAdmin(User user);
}
