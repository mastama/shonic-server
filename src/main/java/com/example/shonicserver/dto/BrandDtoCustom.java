package com.example.shonicserver.dto;

import com.example.shonicserver.model.Brand;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BrandDtoCustom {
    private Integer id;
    private String name;
}
