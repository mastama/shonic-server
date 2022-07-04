package com.example.shonicserver.controller;

import com.example.shonicserver.model.Brand;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.service.BrandService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
@CrossOrigin(origins = "*")
@Api(tags = "Brand")
public class BrandController {
    @Autowired
    BrandService brandServie;

    @GetMapping("/getAllBrand")
    public ResponseEntity<Response> getAllBrand(){
        List<Brand> brandList = brandServie.getListBrand();
        return new ResponseEntity<>(new Response(200,"success",brandList,null), HttpStatus.OK);
    }

}
