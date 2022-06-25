package com.example.shonicserver.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.shonicserver.dto.FlashSaleDto;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.response.CreateProductResponse;
import com.example.shonicserver.repository.ProductRepository;
import com.example.shonicserver.security.CustomOAuth2User;
import com.example.shonicserver.service.ProductService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "*")
@Api(tags = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @PostMapping ("/insertProduct/")
    public ResponseEntity<Response> createNewProduct(@RequestBody ProductDto productDto){

        try {
            CreateProductResponse productDtoInsert=this.productService.insert(productDto);
            return new ResponseEntity<>(new Response(201,"success",productDtoInsert,null), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(500,"Fail create Product",null,null), HttpStatus.CREATED);
        }

    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Response> getOneProduct(@PathVariable UUID id) {
      Product product=this.productService.getById(id);
        return new ResponseEntity<>(new Response(200,"succsess",product,null),HttpStatus.OK);
    }
    @GetMapping("/filter")
    public  ResponseEntity<Response> getFilterProduct(@RequestParam int minPrice,@RequestParam int maxPrice){
        List<ProductDtoCustom> listProducts= this.productService.getFilterByPrice(minPrice,maxPrice);
        return new ResponseEntity<>(new Response(200,"success",listProducts,null),HttpStatus.OK);
    }

    @PostMapping("/imageUpload")
    public ResponseEntity<Object> getImageUrl(@RequestParam(value = "file", required = false) MultipartFile file) {
        String contentType = file.getContentType();
            if(!(contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg"))){
                return new ResponseEntity<>(new Response(400,"Pleas Upload Image File",null,null), HttpStatus.BAD_REQUEST);
            }
            if(file.getSize()>20000000){
                return new ResponseEntity<>(new Response(400,"Image too Large",null,null), HttpStatus.BAD_REQUEST);
            }

        try {
            //System.out.println(imporFile.getName());
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "shonic",
                    "api_key", "599463551394514",
                    "api_secret", "cEKvL2m5_V6cgkQUfIKddKe8v08"));
            Map response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            return new ResponseEntity<>(new Response(200,"Upload Success",response.get("url"),null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(500,"Failed Upload File",null,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //softdelete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") UUID id){
        if( this.productService.delete(id)){
            return new ResponseEntity<>(new Response(200,"success",null,null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(400,"Bad Request",null,null), HttpStatus.BAD_REQUEST);

    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<Response> searchByKeyword(@PathVariable("keyword") String keyword,
                                                 @RequestParam int pageNo,@RequestParam int pageSize) {

        if(keyword==null || pageNo <= 0 ||pageSize <= 0){
            return new ResponseEntity<>(new Response(400, "Invalid Params", null, null), HttpStatus.BAD_REQUEST);
        }

            List<ProductDtoCustom> listProducts = productService.findByKeyword(keyword, pageNo, pageSize);


            Map<String,Object> result= new HashMap<>();

            if(listProducts!=null){
                System.out.println("true");
                result.put("found",true);
                result.put("product",listProducts);


            }
            else{
                System.out.println("false");
                listProducts = productRepository.getProductByDate();
                result.put("found",false);
                result.put("product",listProducts);

            }

        return new ResponseEntity<>(new Response(200, "success", result, null), HttpStatus.OK);


    }

}
