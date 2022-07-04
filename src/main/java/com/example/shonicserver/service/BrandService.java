package com.example.shonicserver.service;


import com.example.shonicserver.model.Brand;
import com.example.shonicserver.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    public List<Brand> getListBrand(){
        List<Brand> brandList = brandRepository.findAll();
        return brandList;
    }
}
