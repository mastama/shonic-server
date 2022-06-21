package com.example.shonicserver.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.shonicserver.dto.FlashSaleDto;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.response.CreateProductResponse;
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

    @GetMapping("/test")
    public String home(){
        return "home page";
    }
    @PostMapping ("/insertProduct")
    public ResponseEntity<Response> insert(@RequestBody ProductDto productDto){
       CreateProductResponse productDtoInsert=this.productService.insert(productDto);
       return new ResponseEntity<>(new Response(201,"success",productDtoInsert,null), HttpStatus.CREATED);

    }

    @GetMapping("getById/{id}")
    public ResponseEntity<Response> getOneProduct(@PathVariable UUID id) {
      Product product=this.productService.getById(id);
        return new ResponseEntity<>(new Response(200,"succsess",product,null),HttpStatus.OK);
    }
   /* @PostMapping ("/insertFlashSale")
    public ResponseEntity<FlashSaleDto> insert(@RequestBody FlashSaleDto flashSaleDto){
        FlashSaleDto flashSaleDtoInsert=this.productService.insertFlashSale(flashSaleDto);
        return new ResponseEntity<>(flashSaleDtoInsert, HttpStatus.CREATED);

    }*/

    @PostMapping("/upload")
    public String getImage(@RequestParam("data") String data, @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            //System.out.println(imporFile.getName());
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dggqgo3rw",
                    "api_key", "466689723189921",
                    "api_secret", "_PzqcDZmcTFu0fFtKLhs1SVnRd4"));


            Map<String, Object> response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            System.out.println("cek response");

            return (String) response.get("url");
        } catch (Exception e) {
            return null;
        }
    }

    //softdelete
    @PutMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") UUID id){
        this.productService.delete(id);
        return new ResponseEntity<>(new Response(200,"success",null,null), HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<Response> viewHomePage(Model model, @PathVariable("keyword") String keyword) {
        List<ProductDtoCustom> listProducts = productService.listAll(keyword);
        //model.addAttribute("listProducts", listProducts);
        model.addAttribute("keyword", keyword);

        return new ResponseEntity<>(new Response(200,"success",listProducts,null), HttpStatus.OK);
    }

}
