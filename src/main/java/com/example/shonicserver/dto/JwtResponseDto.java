package com.example.shonicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class JwtResponseDto {

    private String token;



    private Integer statusCode;
    public JwtResponseDto(String token, Integer statusCode) {
        this.token = token;
        this.statusCode = statusCode;
    }
}