package com.example.shonicserver.payload.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResetPassword {
    private String token;
    private String email;
    private String newPassword;
}
