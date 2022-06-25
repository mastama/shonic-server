package com.example.shonicserver.dto;
import com.example.shonicserver.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlashSaleDto {

    private String id;

    private Timestamp startTime;

    private Timestamp finishTime;

    private Integer flashSale;

    private Product product;

}
