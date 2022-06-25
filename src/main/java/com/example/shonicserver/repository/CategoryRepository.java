package com.example.shonicserver.repository;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Categories,String> {
    public Optional<Categories> findByName(String name);
}
