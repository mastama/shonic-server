package com.example.shonicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private UUID id;
    private UserDto userDto;
    private String nomorHp;
    private String nama;
}
