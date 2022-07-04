package com.example.shonicserver.repository;

import com.example.shonicserver.dto.BrandDtoCustom;
import com.example.shonicserver.dto.CategoryDtoCustom;
import com.example.shonicserver.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

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

    @Query(value = "SELECT new com.example.shonicserver.dto.BrandDtoCustom(b.id,b.name)" +
            "from Brand b " +
            "where lower(b.name) =:brand")
    List<BrandDtoCustom>getByBrand(@Param("brand") String brand);

    @Query(value = "SELECT new com.example.shonicserver.dto.CategoryDtoCustom(c.categoryId,c.name,cp.id)" +
            "from Categories c " +
            "join CategoryParent cp " +
            "on cp.id = c.categoryParent " +
            "where c.name=:category")
    List<CategoryDtoCustom> getByCategory(@Param("category") String category);

<<<<<<< HEAD
    @Query(value = "SELECT new com.example.shonicserver.dto.ProductDtoCustom(p.id,p.createdAt,p.image,p.name, p.price, p.qty,p.discount,r.rating,p.brand,p.categories,p.review) " +
            "FROM Product p " +
            "join Brand b " +
            "on p.brand = b.id " +
            "join Categories c " +
            "on p.categories = c.id "+
            "left join Rating r " +
            "on r.product = p.id " +
            "order by p.createdAt ASC")
    Page<ProductDtoCustom> findAllProductByDate(Pageable pageable);
=======

    List<Product> findAllByCategoriesAndIdNot(Categories categories,UUID id);

    @Query(value = "SELECT p FROM Product p WHERE p.categories IN :categories"
            + " AND (p.price>=:minPrice and p.price <=:maxPrice)"
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)")
    List<Product> getByListCategory(Pageable pageable, @Param("categories")List<Categories> categories,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);

    @Query(value = "SELECT p FROM Product p WHERE p.categories IN :categories"
            + " AND (p.price>=:minPrice and p.price <=:maxPrice)"
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)"
            + " order by p.createdAt DESC")
    List<Product> getByListCategoryDate(Pageable pageable, @Param("categories")List<Categories> categories,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);

    @Query(value = "SELECT p FROM Product p WHERE p.categories IN :categories"
            + " AND (p.price>=:minPrice and p.price <=:maxPrice)"
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)"
            + " order by p.price DESC")
    List<Product> getByListCategoryPriceDesc(Pageable pageable, @Param("categories")List<Categories> categories,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);

    @Query(value = "SELECT p FROM Product p WHERE p.categories IN :categories"
            + " AND (p.price>=:minPrice and p.price <=:maxPrice)"
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)"
            + " order by p.price ASC")
    List<Product> getByListCategoryPriceAsc(Pageable pageable, @Param("categories")List<Categories> categories,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);

//    @Query(value = "SELECT p FROM Product p WHERE p.categories IN :categories")
//    List<Product> getProductByBrand(Pageable pageable, @Param("brand")Brand brand);

    @Query(value = "SELECT p FROM Product p WHERE p.brand = :brand"
            + " AND (p.price>=:minPrice and p.price <=:maxPrice)"
            + " AND (p.rating >= :rating)"
            + " AND (p.discount >= :discount)")
    List<Product> getProductByBrand(Pageable pageable, @Param("brand")Brand brand,@Param("minPrice") Integer minPrice,@Param("maxPrice") Integer maxPrice,@Param("rating") Float rating,@Param("discount")Integer discount);

>>>>>>> ef4ee3f109ad881095a795452beb7a9958cf8cdf
}



