package com.example.shonicserver.service.impl;
import com.example.shonicserver.model.User;
import com.example.shonicserver.dto.UserDto;
import com.example.shonicserver.model.ERole;
import com.example.shonicserver.model.Role;
import com.example.shonicserver.payload.response.UserResponse;
import com.example.shonicserver.repository.RoleRepository;
import com.example.shonicserver.repository.UserRepository;
import com.example.shonicserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
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
    public UserResponse create(UserDto userDto) throws Exception {
        ERole name = ERole.ROLE_CUSTOMER;

        Optional<Role> roleOptional = this.roleRepository.findByName(name);
        Role role;
        if(roleOptional.isPresent()){
            role = roleOptional.get();
        }
        else{
            Role newRole = new Role();
            newRole.setName(name);
            role = roleRepository.save(newRole);
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setUsername(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(roles);

        User userSaved = this.userRepository.save(user);
            UserResponse userResponse = new UserResponse();
            userResponse.setId(userSaved.getId());
            userResponse.setEmail(userSaved.getUsername());
            userResponse.setFullName(userSaved.getFullName());
        return userResponse;
    }

}
