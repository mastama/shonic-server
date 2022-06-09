package com.example.shonicserver.dto;

import com.example.shonicserver.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {

    private String categoryId;
    private String name;
    private Product product;
}
