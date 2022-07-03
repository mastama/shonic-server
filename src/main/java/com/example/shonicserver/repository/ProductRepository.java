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

    @Query(value = "SELECT p " +
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
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)")
    Page<Product> search(@Param("keyword") String keyword, Pageable pageable,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);

    @Query(value = "SELECT p " +
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
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)"
            + " order by p.createdAt DESC")
    Page<Product> searchSortDate(@Param("keyword") String keyword, Pageable pageable,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);


    @Query(value = "SELECT p " +
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
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)"
            + " order by p.price DESC")
    Page<Product> searchSortPriceDesc(@Param("keyword") String keyword, Pageable pageable,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);


    @Query(value = "SELECT p " +
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
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)"
            + " order by p.price ASC")
    Page<Product> searchSortPriceAsc(@Param("keyword") String keyword, Pageable pageable,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);
    @Query(value = "SELECT p " +
        "FROM Product p " +
        "join Brand b " +
        "on p.brand = b.id " +
        "join Categories c " +
        "on p.categories = c.id "+
        "order by p.createdAt DESC")
    Page<Product>getProductByDate(Pageable pageable);
}



