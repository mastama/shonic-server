package com.example.shonicserver.dto;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoCustom {
    private UUID id;
    private LocalDateTime createdAt;
    private String image;
    private String name;
    private Integer price;
    private Integer qty;
//    private String description;
    private Integer discount;
    private Float rating;
    private Brand brand;
    private Categories category;
    private Integer review;
}
