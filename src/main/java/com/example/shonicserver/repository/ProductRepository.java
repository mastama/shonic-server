package com.example.shonicserver.repository;

import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    public List<Product> findProductById(
    @Param("id") UUID id);
}
