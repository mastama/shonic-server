package com.example.shonicserver.service;

import com.example.shonicserver.dto.ProductDetailDTO;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.dto.ProductListDTO;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.response.CreateProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
   public CreateProductResponse insert(ProductDto productDto);

   public ProductDetailDTO getById(UUID id);

   Boolean delete(UUID id);

   List<ProductListDTO> findByKeyword(String keyword, int pageNo, int pageSize,int minPrice, int maxPrice,float rating,int discount,String sort);

   void deleteAllProduct();

   List<ProductListDTO> getNewestProduct(int pageNo, int pageSize);


}
