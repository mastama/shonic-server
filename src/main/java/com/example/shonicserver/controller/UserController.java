package com.example.shonicserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shonic/user")
public class UserController {

    @GetMapping("/home")
    public String home(){
        return "home page";
    }
}
