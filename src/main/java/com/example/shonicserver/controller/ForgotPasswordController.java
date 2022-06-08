package com.example.shonicserver.controller;

import com.alibaba.fastjson.JSON;
import com.example.shonicserver.dto.EmailCheck;
import com.example.shonicserver.dto.ValidateOTP;
import com.example.shonicserver.model.User;
import com.example.shonicserver.payload.request.ResetPassword;
import com.example.shonicserver.repository.UserRepository;
import com.example.shonicserver.service.EmailService;
import com.example.shonicserver.service.OTPService;
import com.example.shonicserver.service.ResetPasswordService;
import io.swagger.annotations.Api;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/forgotpassword")
@CrossOrigin("*")
@Api(tags = "Forgot Password")
public class ForgotPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    public EmailService emailService;

    @Autowired
    public OTPService otpService;

    @Autowired
    public UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<String> generateOTP(@RequestBody EmailCheck emailCheck) throws MessagingException {

        Optional<User> user = userRepository.findByUsername(emailCheck.getEmail());
        String email = null;
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        if(user.isPresent()) {
            email = emailCheck.getEmail();
            int otp = otpService.generateOTP(email);

            String message = "Kode Verifikasi email anda : " + String.valueOf(otp)+"\n\n <br> Kode Verifikasi berlaku selama 2 Menit";
            String finalEmail = email;
            Thread emailThread = new Thread(()-> {
                try {
                    emailService.sendOtpMessage(finalEmail, "OTP - Shonic", message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            emailThread.start();
            result.put("message", "Kode OTP sedang dikirim");
            result.put("status", 200);
            String json = JSON.toJSON(result).toString();
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            result.put("message", "Tidak ada akun dengan email tersebut");
            result.put("status", 400);
            String json = JSON.toJSON(result).toString();
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value ="/validate", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> validateOtp(@RequestBody ValidateOTP validateOTP){

        LinkedHashMap<String, Object> result=new LinkedHashMap<>();

        result.put("message","Entered Otp is NOT valid");
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
                    String token = RandomString.make(30);

                    try {
                        resetPasswordService.updateResetPasswordToken(token, email);
                        result.put("message","Entered Otp is valid");
                        result.put("status", 200);
                        result.put("token", token);
                        final String SUCCESS = JSON.toJSON(result).toString();
                        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
                    } catch (Exception e){
                        result.put("message","Entered Otp is valid but Invalid get Token");
                        result.put("status", 500);
//                        result.put("token", token);
                        result.put("error", e.getMessage());
                        final String SUCCESS = JSON.toJSON(result).toString();
                        return new ResponseEntity<>(SUCCESS, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
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

    @PostMapping("/reset_password")
    public ResponseEntity<String> processResetPassword(@RequestBody ResetPassword resetPassword) {
        String token = resetPassword.getToken();
        String newPassword = resetPassword.getNewPassword();
        String email = resetPassword.getEmail();
        LinkedHashMap<String, Object> response=new LinkedHashMap<>();


        String tokenSaved = resetPasswordService.getTokenSaved(email);
        if(tokenSaved.equals(token)){
            try {
                Optional<User> user = userRepository.findByUsername(email);
                if (user.isPresent()){

                    Boolean resetSucces = resetPasswordService.updatePassword(user.get(),newPassword);
                    if(resetSucces){
                        resetPasswordService.clearToken(email);
                        response.put("message","Password berhasil diubah");
                        response.put("status",200);
                        String json = JSON.toJSON(response).toString();
                        return new ResponseEntity<>(json,HttpStatus.OK);
                    }
                    else {
                        response.put("message","Password baru harus berbeda dengan password lama");
                        response.put("status",400);
                        String json = JSON.toJSON(response).toString();
                        return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
                    }

                }
                else {
                    response.put("message","User tidak ditemukan");
                    response.put("status",400);
                    String json = JSON.toJSON(response).toString();
                    return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
                }
            }catch (Exception e){
                response.put("message","Error update Password");
                response.put("status",500);
                response.put("error",e.getMessage());
                String json = JSON.toJSON(response).toString();
                return new ResponseEntity<>(json,HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        else {
            response.put("message","Token not Valid");
            response.put("status",400);
            String json = JSON.toJSON(response).toString();
            return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
        }
//
    }



}
