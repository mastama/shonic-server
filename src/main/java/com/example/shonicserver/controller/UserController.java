package com.example.shonicserver.controller;
import com.example.shonicserver.dto.JwtResponseDto;
import com.example.shonicserver.dto.LoginDto;
import com.example.shonicserver.dto.UserDto;
import com.example.shonicserver.model.User;
import com.example.shonicserver.payload.Response;
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
    public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) throws Exception {
        // authenticate the user
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(loginDto.getEmail());
            String jwtToken = jwtUtil.generateToken(userDetails);

            JwtResponseDto jwtResponse = new JwtResponseDto(jwtToken);

            return new ResponseEntity<>(new Response(jwtResponse, "success login", "200"), HttpStatus.ACCEPTED);
        }catch (Exception e){
            JwtResponseDto jwtResponse = new JwtResponseDto();
            return new ResponseEntity<>(new Response(jwtResponse, "Email dan password anda salah", "404 "), HttpStatus.BAD_REQUEST);
            //return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // create registration
    @PostMapping("/register")
    public ResponseEntity<UserDto>create(@RequestBody UserDto userDto) throws Exception {
        try {
            UserDto user = userService.create(userDto);
            return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<UserDto>( HttpStatus.BAD_REQUEST);
        }

    }
}
