package com.example.shonicserver.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Service
public class OTPService {
    private static final Integer EXPIRE_MINS = 2;
    private LoadingCache otpCache;
    public OTPService() {
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader() {

                    @Override
                    public Integer load(Object o) throws Exception {
                        return 0;
                    }
                });
    }
    public int generateOTP(String key){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }
    public int getOtp(String key){
        try{
            return (int) otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

    public void clearOTP(String key){
        otpCache.invalidate(key);
    }
}
