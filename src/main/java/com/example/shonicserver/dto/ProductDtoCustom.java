package com.example.shonicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoCustom {
    private String pName;
    private Integer price;
    private Integer qty;
    private String bName;
    private String cName;
}
