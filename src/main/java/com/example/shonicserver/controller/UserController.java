package com.example.shonicserver.controller;
import com.example.shonicserver.dto.JwtResponseDto;
import com.example.shonicserver.dto.LoginDto;
import com.example.shonicserver.dto.UserDto;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.response.UserResponse;
import com.example.shonicserver.service.JpaUserDetailsService;
import com.example.shonicserver.service.UserService;
import com.example.shonicserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(){
        return "home page";
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto) throws Exception {
        // authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(loginDto.getEmail());
        String jwtToken = jwtUtil.generateToken(userDetails);
        JwtResponseDto jwtResponse = new JwtResponseDto(jwtToken, "200");

        return new ResponseEntity<JwtResponseDto>(jwtResponse, HttpStatus.ACCEPTED);
    }

    // create registration
    @PostMapping("/register")
    public ResponseEntity<Response>create(@RequestBody UserDto userDto) throws Exception {
        LinkedHashMap<String, Object> result=new LinkedHashMap<>();
        try {
            UserResponse user = userService.create(userDto);

            return new ResponseEntity<>(new Response(200,"succes",user,null),HttpStatus.OK);

        } catch (Exception e){
            System.out.println(e.getMessage());
            result.put("status",500);
            result.put("message","register error");
            result.put("data",e.getMessage());
            return new ResponseEntity<>(new Response(500,"failed",null,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }




}
