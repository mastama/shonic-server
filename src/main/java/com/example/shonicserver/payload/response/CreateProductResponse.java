package com.example.shonicserver.payload.response;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.model.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreateProductResponse {
    private UUID id;
    private String name;
    private Integer price;
    private Integer qty;
    private Categories categoriesList;
    private Brand brand;
    private String image;
    private String imageFull;
}
