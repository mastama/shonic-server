package com.example.shonicserver.service;

import com.example.shonicserver.dto.BranDtoCustom;
import com.example.shonicserver.dto.CategoryDtoCustom;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.response.CreateProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
   public CreateProductResponse insert(ProductDto productDto);

  //List<ProductDto> findById(UUID id);

   public Product getById(UUID id);

   Boolean delete(UUID id);

   List<ProductDtoCustom> findByKeyword(String keyword,int pageNo,int pageSize);

   List<ProductDtoCustom> getFilterByPrice(int minPrice, int maxPrice);


   void deleteAllProduct();

   List<BranDtoCustom> getByBrand(String brand);

   List<CategoryDtoCustom> getByCategory(String category);

   //List<ProductDtoCustom> listAll(String keyword, int pageNo, int pageSize);

}
