package com.example.shonicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private AddressesDto addressesdto;
}
