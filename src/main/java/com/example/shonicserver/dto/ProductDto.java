package com.example.shonicserver.dto;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.FlashSale;
import com.example.shonicserver.model.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDto {

    private String name;
    private Integer price;
    private Integer qty;
    private String category;
    @JsonProperty("category_parent")
    private String categoryParent;
    private String brand;
    private Float weight;
    private String description;
    private Integer discount;
    private String imageUrl;

}
