package com.example.shonicserver.service;

import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.response.CreateProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
   public CreateProductResponse insert(ProductDto productDto);

  //List<ProductDto> findById(UUID id);

   public Product getById(UUID id);

   Boolean delete(UUID id);

   List<ProductDtoCustom> listAll(String keyword,int pageNo,int pageSize);


   //List<ProductDtoCustom> listAll(String keyword, int pageNo, int pageSize);

}
