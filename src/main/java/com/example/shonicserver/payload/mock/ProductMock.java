package com.example.shonicserver.payload.mock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMock {
    private String id;
    private String name;
    private String createdAt;
    private String image;
    private Integer price;
    private Integer qty;
    private String description;
    private String discount;
    private Float rating;
    private Map<String,Object> brand;
    private Map<String,Object> categories;


}
