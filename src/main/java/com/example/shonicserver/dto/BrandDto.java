package com.example.shonicserver.dto;

import com.example.shonicserver.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BrandDto {

    private Integer id;
    private String name;
    private ProductDto productDto;

}
