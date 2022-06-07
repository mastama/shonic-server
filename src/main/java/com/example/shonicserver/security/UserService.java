package com.example.shonicserver.security;

import com.example.shonicserver.model.Provider;
import com.example.shonicserver.model.User;
import com.example.shonicserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    public void processOAuthPostLogin(String username) {
        User existUser = repo.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
           // newUser.setEnabled(true);
            System.out.println("masuk sini");
            repo.save(newUser);

        }else {
            System.out.println("masuk sini 2");
        }

    }
}
