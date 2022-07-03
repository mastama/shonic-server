package com.example.shonicserver.payload.mock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String createdAt;
    private String image;
    private Integer price;
    private Integer qty;
    private String description;
    private String discount;
    private Double rating;

}
