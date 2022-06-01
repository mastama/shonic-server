package com.example.shonicserver.repository;

import com.example.shonicserver.model.ERole;
import com.example.shonicserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    public Optional<Role> findByName(ERole name);
}

