package com.example.shonicserver.controller;
import javax.mail.MessagingException;

import com.alibaba.fastjson.JSON;
import com.example.shonicserver.dto.EmailCheck;
import com.example.shonicserver.dto.ValidateOTP;
import com.example.shonicserver.helper.EmailTemplate;
import com.example.shonicserver.model.User;
import com.example.shonicserver.repository.UserRepository;
import com.example.shonicserver.service.EmailService;
import com.example.shonicserver.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/otp")
public class OTPController {
    @Autowired
    public OTPService otpService;

    @Autowired
    public EmailService emailService;

    @Autowired
    public UserRepository userRepository;
    @PostMapping("/send")
    public ResponseEntity<String> generateOTP(@RequestBody EmailCheck emailCheck) throws MessagingException{

        Optional<User> user= userRepository.findByUsername(emailCheck.getEmail());
        String email =null;
        LinkedHashMap<String, Object> result=new LinkedHashMap<>();
        if(user.isPresent()){
            result.put("message","Email Sudah Terdaftar");
            result.put("status",400);
            String json =JSON.toJSON(result).toString();
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
        else{
            email = emailCheck.getEmail();
            int otp = otpService.generateOTP(email);

            String message = "Kode Verifikasi email anda : " + String.valueOf(otp)+"\n\n <br> Kode Verifikasi berlaku selama 1 Menit";
            String finalEmail = email;
            Thread emailThread = new Thread(()->{
                try {
                    emailService.sendOtpMessage(finalEmail, "OTP - Shonic", message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            emailThread.start();
            result.put("message","Kode OTP sedang dikirim");
            result.put("status",200);
            String json =JSON.toJSON(result).toString();
            return new ResponseEntity<>(json, HttpStatus.OK);

        }


        //Generate The Template to send OTP
//        EmailTemplate template = new EmailTemplate("SendOtp.html");
//        Map replacements = new HashMap();
//        replacements.put("user", username);
//        replacements.put("otpnum", String.valueOf(otp));




    }

    @RequestMapping(value ="/validate", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> validateOtp(@RequestBody  ValidateOTP validateOTP){

        LinkedHashMap<String, Object> result=new LinkedHashMap<>();
        result.put("message","Entered Otp is valid");
        result.put("status",200);
        final String SUCCESS = JSON.toJSON(result).toString();
        result.put("message","Entered Otp is NOT valid. Please Retry!");
        result.put("status",400);
        final String FAIL = JSON.toJSON(result).toString();
        String email= validateOTP.getEmail();
        int otpnum = validateOTP.getOtp();
        //Validate the Otp
        if( otpnum >= 0){
            System.out.println(otpnum);

            int serverOtp = otpService.getOtp(email);
            System.out.println(serverOtp);
            if(serverOtp > 0){
                if(otpnum == serverOtp){
                    otpService.clearOTP(email);
                    return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
                }
            }else {
                return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

}
