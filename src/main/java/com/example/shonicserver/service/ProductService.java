package com.example.shonicserver.service;

import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.response.CreateProductResponse;
import java.util.UUID;

import java.util.UUID;

public interface ProductService {
   public CreateProductResponse insert(ProductDto productDto);

  //List<ProductDto> findById(UUID id);

   public Product getById(UUID id);

    // List<Product> findById2(UUID id);
   void delete(UUID id);

   //public FlashSaleDto insertFlashSale(FlashSaleDto flashSaleDto);
}
