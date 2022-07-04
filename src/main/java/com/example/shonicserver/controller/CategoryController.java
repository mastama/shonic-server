package com.example.shonicserver.controller;


import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.CategoryParent;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.response.CategoryResponse;
import com.example.shonicserver.payload.response.SubCategoryResponse;
import com.example.shonicserver.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "*")
@Api(tags = "Category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("getAll")
    public ResponseEntity<Response> getAllCategory(){
        List<Categories> categoryList =categoryService.getAllCategory();
        Map<CategoryParent, List<Categories>> mapCategories = categoryList.stream().collect(Collectors.groupingBy(Categories::getCategoryParent));

        List<CategoryResponse> categoryResponseList =new ArrayList<>();
        for (Map.Entry<CategoryParent, List<Categories>> entry : mapCategories.entrySet()) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(entry.getKey().getId());
            categoryResponse.setName(entry.getKey().getName());
            categoryResponse.setSubCategories(entry.getValue().stream().map(SubCategoryResponse::of).collect(Collectors.toList()));
            categoryResponseList.add(categoryResponse);
        }
        return new ResponseEntity<>(new Response(200,"success",categoryResponseList,null), HttpStatus.OK);
    }
}
