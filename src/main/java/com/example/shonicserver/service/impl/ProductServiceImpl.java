package com.example.shonicserver.service.impl;

import com.example.shonicserver.dto.*;

import com.example.shonicserver.model.*;
import com.example.shonicserver.payload.response.CreateProductResponse;
import com.example.shonicserver.repository.BrandRepository;
import com.example.shonicserver.repository.CategoryRepository;
import com.example.shonicserver.repository.ProductRepository;
import com.example.shonicserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CreateProductResponse insert(ProductDto productDto) {


        Product product=new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQty(productDto.getQty());
        product.setDate(product.getDate());

        //get brand byname
        String name = productDto.getBrand();
        Optional<Brand> brandOptional = this.brandRepository.findByName(name);
        Brand brand;
        if(brandOptional.isPresent()){
            brand = brandOptional.get();
        }
        else{
            Brand newBrand = new Brand();
            newBrand.setName(name);
           brand = brandRepository.save(newBrand);
        }

        product.setBrand(brand);

        //product.setImage(productDto.getImage());

        product.setImageFull(false);

        //create list categories
       /*List<Categories> categoriesList=new ArrayList<>();
        for (CategoriesDto categoriesDto:productDto.getCategory()){
            Categories categories=new Categories();
            categories.setName(categoriesDto.getName());
            categories.setProduct(product);
            categoriesList.add(categories);


        }*/
        String nameCategory = productDto.getCategory();
        Optional<Categories> categoriesOptional = this.categoryRepository.findByName(nameCategory);
        Categories categories;
        if(categoriesOptional.isPresent()){
            categories = categoriesOptional.get();
        }
        else{
           Categories newCategory = new Categories();
          newCategory.setName(nameCategory);
            categories = categoryRepository.save(newCategory);
        }

       product.setCategories(categories);


        //create list rating
        /*List<Rating>ratingList=new ArrayList<>();
        for (RatingDto ratingDto:productDto.getRatingDtoList()){
            Rating rating=new Rating();
            rating.setRating(ratingDto.getRating());
            rating.setUser(ratingDto.getUser());
            rating.setProduct(product);
            rating.setUlasan(ratingDto.getUlasan());
            ratingList.add(rating);
        }
        product.setRatingList(ratingList);*/
        //create list flashsale
        /*List<FlashSale>flashSaleList=new ArrayList<>();
        for (FlashSaleDto flashSaleDto:productDto.getDiscountDtoList()){
            FlashSale flashSale=new FlashSale();
            flashSale.setStartTime(flashSaleDto.getStartTime());
            flashSale.setFinishTime(flashSaleDto.getFinishTime());
            flashSale.setFlashSale(flashSaleDto.getFlashSale());
            flashSale.setProduct(product);
            flashSaleList.add(flashSale);

        }
        product.setDiscountList(flashSaleList);*/

        //dto product
        Product productInserted = productRepository.save(product);
        CreateProductResponse productDtoInserted =new CreateProductResponse();
        productDtoInserted.setName(productInserted.getName());

        productDtoInserted.setBrand(productInserted.getBrand());
        productDtoInserted.setQty(productInserted.getQty());
        productDtoInserted.setPrice(productInserted.getPrice());
        //productDtoInserted.setImage(productDto.getImage());
        productDtoInserted.setId(productInserted.getId());
        productDtoInserted.setImageFull(false);

        //list categories Dto
        /*List<CategoriesDto>categoriesDtoList=new ArrayList<>();
        for (Categories categories:productInserted.getCategoriesList()){
            CategoriesDto categoriesDto=new CategoriesDto();
            categoriesDto.setName(categories.getName());
            categoriesDto.setCategoryId(categories.getCategoryId());
            categoriesDtoList.add(categoriesDto);


        }*/
        productDtoInserted.setCategoriesList(product.getCategories());

        //list rating
        /*List<RatingDto>ratingDtoList=new ArrayList<>();
        for (Rating rating:productInserted.getRatingList()){
            RatingDto ratingDto=new RatingDto();
            ratingDto.setRating(rating.getRating());
            ratingDto.setUser(rating.getUser());
            ratingDto.setId(rating.getId());
            ratingDto.setUlasan(rating.getUlasan());
            ratingDtoList.add(ratingDto);
        }
        productDtoInserted.setRatingDtoList(ratingDtoList);*/
        //list discount(flash sale)
        /*List<FlashSaleDto>flashSaleDtoList=new ArrayList<>();
        for (FlashSale flashSale:productInserted.getDiscountList()){
            FlashSaleDto flashSaleDto=new FlashSaleDto();
            flashSaleDto.setStartTime(flashSale.getStartTime());
            flashSaleDto.setFinishTime(flashSale.getFinishTime());
            flashSaleDto.setFlashSale(flashSale.getFlashSale());
            flashSaleDto.setId(flashSale.getId());
            flashSaleDtoList.add(flashSaleDto);

        }
        productDtoInserted.setDiscountDtoList(flashSaleDtoList);*/


        return productDtoInserted;

    }

   /* @Override
    public FlashSaleDto insertFlashSale(FlashSaleDto flashSaleDto) {
        FlashSale flashSale=new FlashSale();
        flashSale.setFinishTime(flashSaleDto.getFinishTime());
        flashSale.setStartTime(flashSaleDto.getStartTime());
        flashSale.setFlashSale(flashSale.getFlashSale());
        return null;
    }*/
}
