package com.example.shonicserver.repository;

import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    /*@Query("SELECT p FROM Product p WHERE p.id = :id")
    public List<Product> findProductById(
    @Param("id") UUID id);*/

    @Query(value = "SELECT new com.example.shonicserver.dto.ProductDtoCustom(p.id,p.date,p.image,p.name, p.price, p.qty,p.description,p.discount,r.rating,p.brand,p.categories) " +
            "FROM Product p " +
            "join Brand b " +
            "on p.brand = b.id " +
            "join Categories c " +
            "on p.categories = c.id "+
           "left join Rating r " +
           "on r.product = p.id " +
            " WHERE lower(p.name) LIKE %:keyword%"
            + " OR lower(b.name) LIKE %:keyword%"
            + " OR lower(c.name) LIKE %:keyword%"   )
    List<ProductDtoCustom> search(@Param("keyword") String keyword);
}
