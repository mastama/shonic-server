package com.example.shonicserver.service;

import com.example.shonicserver.dto.JpaUserDetails;
import com.example.shonicserver.model.User;
import com.example.shonicserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException(username+" is not found"));

        return new JpaUserDetails(user);
    }
}
