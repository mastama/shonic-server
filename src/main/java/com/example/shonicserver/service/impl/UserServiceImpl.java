package com.example.shonicserver.service.impl;
import com.example.shonicserver.dto.RegisterDto;
import com.example.shonicserver.model.User;
import com.example.shonicserver.model.ERole;
import com.example.shonicserver.model.Role;
import com.example.shonicserver.payload.response.UserResponse;
import com.example.shonicserver.repository.RoleRepository;
import com.example.shonicserver.repository.UserRepository;
import com.example.shonicserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

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
    public UserResponse create(RegisterDto registerDto) throws Exception {
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
        user.setUsername(registerDto.getEmail());
        user.setFullName(registerDto.getFullname());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setRoles(roles);


        User userSaved = this.userRepository.save(user);
            UserResponse userResponse = new UserResponse();
            userResponse.setId(userSaved.getId());
            userResponse.setEmail(userSaved.getUsername());
            userResponse.setFullName(userSaved.getFullName());
        return userResponse;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByUsername(email);
    }

    public void makeAsAdmin(User user){
        Optional<Role> roleOptional = roleRepository.findByName(ERole.ROLE_ADMIN);
        Role roleAdmin;
        if(roleOptional.isPresent()){
            roleAdmin = roleOptional.get();
        }else{
            Role role = new Role();
            role.setName(ERole.ROLE_ADMIN);
            roleAdmin = roleRepository.save(role);
        }
        Role roleCustomer = roleRepository.findByName(ERole.ROLE_CUSTOMER).get();
        Set<Role> newRole = new HashSet<>();
        newRole.add(roleAdmin);
        newRole.add(roleCustomer);
        user.setRoles(newRole);
        userRepository.save(user);

    }

}
