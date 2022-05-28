package com.example.shonicserver.repository;

import com.example.shonicserver.model.ERole;
import com.example.shonicserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    public Optional<Role> findByName(ERole name);
}

