package com.example.shonicserver.service;

import com.example.shonicserver.dto.FlashSaleDto;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.payload.response.CreateProductResponse;

public interface ProductService {
   public CreateProductResponse insert(ProductDto productDto);

   //public FlashSaleDto insertFlashSale(FlashSaleDto flashSaleDto);
}
