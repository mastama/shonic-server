package com.example.shonicserver.service.impl;
import com.example.shonicserver.model.User;
import com.example.shonicserver.dto.UserDto;
import com.example.shonicserver.model.ERole;
import com.example.shonicserver.model.Role;
import com.example.shonicserver.repository.RoleRepository;
import com.example.shonicserver.repository.UserRepository;
import com.example.shonicserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto create(UserDto userDto) throws Exception {
        ERole name ;
        name = ERole.ROLE_CUSTOMER;


        Role role = this.roleRepository.findByName(name).get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setUsername(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(roles);

        User userSaved = this.userRepository.save(user);
            UserDto userDtoResponse=new UserDto();
            userDtoResponse.setId(userSaved.getId());
            userDtoResponse.setEmail(userSaved.getUsername());
        return userDtoResponse;
    }

}
