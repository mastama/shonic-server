package com.example.shonicserver.service;

import com.example.shonicserver.model.Categories;
import com.example.shonicserver.model.CategoryParent;
import com.example.shonicserver.repository.CategoryParentRepository;
import com.example.shonicserver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryParentRepository categoryParentRepository;

    public List<Categories> getAllCategory(){
        List<Categories> categoryParentList = categoryRepository.findAll();
        return categoryParentList;
    }
}
