package com.example.shonicserver.dto;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoCustom {
    private UUID id;
    private String name;
    private String createdAt;
    private String image;
    private Integer price;
    private Integer qty;
    private String description;
    private Double discount;
    private Integer rating;
    private Brand branName;
    private Categories categoryName;
}
