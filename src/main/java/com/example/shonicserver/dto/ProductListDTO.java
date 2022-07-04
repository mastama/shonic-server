package com.example.shonicserver.dto;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String image;
    private String name;
    private Integer price;
    @JsonProperty("after_discount")
    private Integer afterDiscount;
    private Integer qty;
    private Integer discount;
    private Float rating;
    private Brand brand;
    private Categories category;
    private Integer review;

    public static ProductListDTO of(Product data){
        return ProductListDTO.builder()
                .id(data.getId())
                .createdAt(data.getCreatedAt())
                .image(data.getImage())
                .name(data.getName())
                .price(data.getPrice())
                .qty(data.getQty())
                .discount(data.getDiscount()==null? 0 : data.getDiscount())
                .rating(data.getRating() == null ? 0 : data.getRating())
                .brand(data.getBrand())
                .category(data.getCategories())
                .review(data.getReview() == null ? 0 : data.getReview())
                .afterDiscount(data.getDiscount() > 0? data.getPrice()-(data.getPrice() * data.getDiscount() / 100) : data.getDiscount() )
                .build();
    }
}
