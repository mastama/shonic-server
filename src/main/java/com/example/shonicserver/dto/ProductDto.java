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


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDto {

    private String name;
    private Integer price;
    private Integer qty;
    private String category;
    private String brand;
    private Boolean isDeleted;
    //private String image;
   // private Boolean imageFull;
   // private List<RatingDto> ratingDtoList;
}
