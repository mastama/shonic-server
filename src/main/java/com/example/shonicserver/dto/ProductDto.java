package com.example.shonicserver.dto;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.FlashSale;
import com.example.shonicserver.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String id;
    private String name;
    private Integer price;
    private Integer qty;
    private Timestamp date;
    private List<Categories> categories;
    private Brand brand;
    private List<FlashSale> discount;
    private String image;
    private Boolean imageFull;
    private List<Rating> rating;
}
