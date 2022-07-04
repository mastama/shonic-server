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
public interface CategoryRepository extends JpaRepository<Categories,String> {
    public Optional<Categories> findByName(String name);

    @Query(value = "SELECT c FROM Categories c WHERE lower(c.name) = :name")
    public List<Categories> findAllByName(@Param("name") String name);

    @Query(value = "SELECT * FROM categories c WHERE c.id = :id OR c.parent= :id",nativeQuery = true)
    List<Categories> getAllCategoriesbyParent(@Param("id")Long id);


}
