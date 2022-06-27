package com.example.shonicserver.controller;
import com.alibaba.fastjson.JSON;
import com.example.shonicserver.dto.JwtResponseDto;
import com.example.shonicserver.dto.LoginDto;
import com.example.shonicserver.dto.RegisterDto;
import com.example.shonicserver.model.User;
import com.example.shonicserver.payload.response.UserResponse;
import com.example.shonicserver.repository.UserRepository;
import com.example.shonicserver.service.JpaUserDetailsService;
import com.example.shonicserver.service.UserService;
import com.example.shonicserver.util.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@Api(tags = "Auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto) throws Exception {
        // authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(loginDto.getEmail());
        String jwtToken = jwtUtil.generateToken(userDetails);
        JwtResponseDto jwtResponse = new JwtResponseDto(jwtToken, 200,"success");

        return new ResponseEntity<JwtResponseDto>(jwtResponse, HttpStatus.ACCEPTED);
    }

    // create registration
    @PostMapping("/register")
    public ResponseEntity<String>create(@RequestBody RegisterDto registerDto) throws Exception {
        LinkedHashMap<String, Object> result=new LinkedHashMap<>();
        Optional<User> userOptional= userRepository.findByUsername(registerDto.getEmail());
        if(userOptional.isPresent()){
            result.put("message","Email Sudah Terdaftar");
            result.put("status",400);
            result.put("error","Email Invalid");
            String json =JSON.toJSON(result).toString();
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
        else{
            try {
                UserResponse user = userService.create(registerDto);
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(registerDto.getEmail(),registerDto.getPassword()));

                UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(registerDto.getEmail());
                String jwtToken = jwtUtil.generateToken(userDetails);

                result.put("token",jwtToken);
                result.put("message","Register Berhasil");
                result.put("status",200);
                result.put("data",user);
                String json =JSON.toJSON(result).toString();
                return new ResponseEntity<>(json, HttpStatus.OK);

            } catch (Exception e){
                System.out.println(e.getMessage());
                result.put("message","Register Gagal");
                result.put("status",500);
                result.put("error",e.getMessage());
                String json =JSON.toJSON(result).toString();
                return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }






    }






}
