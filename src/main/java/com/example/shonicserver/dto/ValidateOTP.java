package com.example.shonicserver.dto;

import lombok.ToString;

@ToString
public class ValidateOTP {
    private String email;
    private int otp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
