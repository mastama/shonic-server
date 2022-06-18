package com.example.shonicserver.dto;

import com.example.shonicserver.model.Product;
import lombok.*;

import java.util.List;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BrandDto {

    private Integer id;
    private String name;
    private ProductDto productDto;

}
