package com.example.shonicserver.service;

import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.payload.response.CreateProductResponse;;import java.util.UUID;

public interface ProductService {
   public CreateProductResponse insert(ProductDto productDto);

   void delete(UUID id);

   //public FlashSaleDto insertFlashSale(FlashSaleDto flashSaleDto);
}
