package com.example.shonicserver.repository;

import com.example.shonicserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
