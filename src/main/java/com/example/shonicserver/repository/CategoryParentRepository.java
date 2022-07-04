package com.example.shonicserver.repository;

import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.CategoryParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryParentRepository extends JpaRepository<CategoryParent,Long> {

    Optional<CategoryParent> findByName(String name);

}
