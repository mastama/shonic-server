package com.example.shonicserver.dto;

import com.example.shonicserver.model.Product;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoriesDto {

    private Long categoryId;
    private String name;
    private ProductDto productDto;


}
