package com.example.shonicserver.repository;
import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {
    public Optional<Brand> findByName(String name);

    @Query(value = "SELECT c FROM Brand c WHERE lower(c.name) = :name")
    Optional<Brand> findAllByName(@Param("name") String name);
}
