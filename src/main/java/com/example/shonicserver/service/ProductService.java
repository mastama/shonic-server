package com.example.shonicserver.service;


import com.example.shonicserver.dto.ProductDetailDTO;
import com.example.shonicserver.dto.BrandDtoCustom;
import com.example.shonicserver.dto.CategoryDtoCustom;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductListDTO;
import com.example.shonicserver.payload.response.CreateProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
   public CreateProductResponse insert(ProductDto productDto);

   public ProductDetailDTO getById(UUID id);

   Boolean delete(UUID id);

   List<ProductListDTO> findByKeyword(String keyword, int pageNo, int pageSize,int minPrice, int maxPrice,float rating,int discount,String sort);

   void deleteAllProduct();

   List<ProductListDTO> getNewestProduct(int pageNo, int pageSize);

   List<BrandDtoCustom> getByBrand(String brand);

   List<CategoryDtoCustom> getByCategory(String category);

<<<<<<< HEAD
    List<ProductDtoCustom> getAllProductBydate(int pageNo, int pageSize);

    //List<ProductDtoCustom> listAll(String keyword, int pageNo, int pageSize);
=======
   List<ProductListDTO> getSimmilarityProduct(UUID productId);

   List<ProductListDTO> getProductCategories(int pageNo,int pageSize,String name,int minPrice, int maxPrice,float rating,int discount,String sort);
>>>>>>> ef4ee3f109ad881095a795452beb7a9958cf8cdf

   List<ProductListDTO> getProductBrand(int pageNo,int pageSize,String name,int minPrice, int maxPrice,float rating,int discount,String sort);
}
