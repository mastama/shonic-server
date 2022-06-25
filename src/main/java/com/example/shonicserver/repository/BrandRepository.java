package com.example.shonicserver.repository;
import com.example.shonicserver.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {
    public Optional<Brand> findByName(String name);
}
