package com.example.shonicserver.repository;

import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    /*@Query("SELECT p FROM Product p WHERE p.id = :id")
    public List<Product> findProductById(
    @Param("id") UUID id);*/

    @Query(value = "SELECT new com.example.shonicserver.dto.ProductDtoCustom(p.id,p.createdAt,p.image,p.name, p.price, p.qty,p.discount,p.rating,p.brand,p.categories,p.review) " +
            "FROM Product p " +
            "join Brand b " +
            "on p.brand = b.id " +
            "join Categories c " +
            "on p.categories = c.id "+
           "left join Rating r " +
           "on r.product = p.id " +
            " WHERE ( lower(p.name) LIKE %:keyword%"
            + " OR lower(b.name) LIKE %:keyword%"
            + " OR lower(c.name) LIKE %:keyword%) AND (p.price>=:minPrice and p.price <=:maxPrice)"
            + " AND p.rating >= :rating")
    Page<ProductDtoCustom> search(@Param("keyword") String keyword, Pageable pageable,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating")Float rating);

@Query(value = "SELECT new com.example.shonicserver.dto.ProductDtoCustom(p.id,p.createdAt,p.image,p.name, p.price, p.qty,p.discount,p.rating,p.brand,p.categories,p.review) " +
        "FROM Product p " +
        "join Brand b " +
        "on p.brand = b.id " +
        "join Categories c " +
        "on p.categories = c.id "+
        "left join Rating r " +
        "on r.product = p.id " +
        "order by p.createdAt ASC")
    List<ProductDtoCustom>getProductByDate();

    @Query(value = "SELECT new com.example.shonicserver.dto.ProductDtoCustom(p.id,p.createdAt,p.image,p.name, p.price, p.qty,p.discount,p.rating,p.brand,p.categories,p.review) " +
            "FROM Product p " +
            "join Brand b " +
            "on p.brand = b.id " +
            "join Categories c " +
            "on p.categories = c.id "+
            "left join Rating r " +
            "on r.product = p.id " +
            "where p.price>=:minPrice and p.price <=:maxPrice")
    List<ProductDtoCustom> getFilterPrice(@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice);
}
