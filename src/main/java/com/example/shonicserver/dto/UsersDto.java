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
public class UsersDto {
    private UUID id;
    private UsersDto usersDto;
    private String email;
    private String password;
    private String role;
    private AddressesDto addressesdto;
}
