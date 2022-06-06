package com.example.shonicserver.service;

import com.example.shonicserver.model.User;
import com.example.shonicserver.repository.UserRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    private LoadingCache resetToken;
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(email);
        if (user.isPresent()) {
            resetToken = CacheBuilder.newBuilder().
                    expireAfterWrite(2, TimeUnit.MINUTES)
                    .build(new CacheLoader() {

                        @Override
                        public Integer load(Object o) throws Exception {
                            return 0;
                        }
                    });
            resetToken.put(email, token);
        } else {
            throw new UsernameNotFoundException("Could not find user with email: " + email);
        }
    }

//    public Optional<User> getByResetPasswordToken(String token) {
//        return userRepository.findByResetPasswordToken(token);
//    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

//        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}
