package com.example.shonicserver.dto;

import com.example.shonicserver.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private String id;
    private Timestamp startTime;
    private Timestamp finishTime;
    private Integer discount;
    private Product product;
}
