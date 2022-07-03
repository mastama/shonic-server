package com.example.shonicserver.service.impl;

import com.example.shonicserver.dto.*;

import com.example.shonicserver.model.*;
import com.example.shonicserver.payload.response.CreateProductResponse;
import com.example.shonicserver.repository.BrandRepository;
import com.example.shonicserver.repository.CategoryParentRepository;
import com.example.shonicserver.repository.CategoryRepository;
import com.example.shonicserver.repository.ProductRepository;
import com.example.shonicserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryParentRepository categoryParentRepository;

    @Override
    public CreateProductResponse insert(ProductDto productDto) {


        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQty(productDto.getQty());
        product.setDescription(productDto.getDescription());
        product.setWeight(productDto.getWeight());

        if(productDto.getDiscount() > 0 && productDto.getDiscount()<=100)
        product.setDiscount(productDto.getDiscount());
        else product.setDiscount(0);

        if(!(productDto.getImageUrl() == null || productDto.getImageUrl().isEmpty())){
          if(productDto.getImageUrl().contains("https://res.cloudinary.com/shonic/image/upload")){
              String imageUrl = productDto.getImageUrl();
              String url1 = imageUrl.substring(0,46);
              String url2 = imageUrl.substring(47,imageUrl.length());
              String resolusi = "/w_300,h_300/";
              String urlMini = url1+resolusi+url2;

              product.setImage(urlMini);
              product.setImageFull(imageUrl);
          }
          else {
              product.setImage(productDto.getImageUrl());
              product.setImageFull(productDto.getImageUrl());
          }
        }


        //get brand byname
        String name = productDto.getBrand();
        Optional<Brand> brandOptional = this.brandRepository.findByName(name);
        Brand brand;
        if (brandOptional.isPresent()) {
            brand = brandOptional.get();
        } else {
            Brand newBrand = new Brand();
            newBrand.setName(name);
            brand = brandRepository.save(newBrand);
        }

        product.setBrand(brand);

        String nameCategory = productDto.getCategory().toLowerCase();
        Optional<Categories> categoriesOptional = this.categoryRepository.findByName(nameCategory);
        Categories categories;
        if (categoriesOptional.isPresent()) {
            categories = categoriesOptional.get();
        } else {
            CategoryParent categoryParent ;
            Optional<CategoryParent> categoryParentOptional = this.categoryParentRepository.findByName(productDto.getCategoryParent().toLowerCase());
            if(categoryParentOptional.isPresent()){
                categoryParent =categoryParentOptional.get();
            }else {
                CategoryParent newCategoryParent = new CategoryParent();
                newCategoryParent.setName(productDto.getCategoryParent().toLowerCase());
                 categoryParent = categoryParentRepository.save(newCategoryParent);
            }
            Categories newCategory = new Categories();
            newCategory.setName(nameCategory);
            newCategory.setCategoryParent(categoryParent);
            categories = categoryRepository.save(newCategory);
        }
        product.setCategories(categories);

        //dto product
        Product productInserted = productRepository.save(product);
        CreateProductResponse productDtoInserted = new CreateProductResponse();
        productDtoInserted.setName(productInserted.getName());

        productDtoInserted.setBrand(productInserted.getBrand());
        productDtoInserted.setQty(productInserted.getQty());
        productDtoInserted.setPrice(productInserted.getPrice());

        productDtoInserted.setId(productInserted.getId());
        productDtoInserted.setImageFull(productInserted.getImageFull());
        productDtoInserted.setCategoriesList(product.getCategories());
        return productDtoInserted;

    }

    @Override
    public Product getById(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = new Product();
        if (productOptional.isPresent()) {
            product = productOptional.get();
            return convertProductToProductDto(product);
        }
       return null;
    }
    private Product convertProductToProductDto (Product productCreated) {
        //create new
        if (productCreated.getId() == null) {
            Product productDtoCreated = new Product();
            productDtoCreated.setPrice(productCreated.getPrice());
            productDtoCreated.setQty(productCreated.getQty());
            productDtoCreated.setName(productCreated.getName());
            productDtoCreated.getBrand();
            //productDtoCreated.getCategory();
            return productDtoCreated;

        }else {
            Optional<Product>existingProductOptional=productRepository.findById(productCreated.getId());
            Product existingProduct=existingProductOptional.get();
            Product productDtoCreated =new Product();
            productDtoCreated.setId(existingProduct.getId());
            productDtoCreated.setName(existingProduct.getName());
            productDtoCreated.setQty(existingProduct.getQty());
            productDtoCreated.setPrice(existingProduct.getPrice());

            Brand brandDtoUpdate=new Brand();
            brandDtoUpdate.setId(existingProduct.getBrand().getId());
            brandDtoUpdate.setName(existingProduct.getBrand().getName());
            productDtoCreated.setBrand(brandDtoUpdate);

            Categories categoriesDtoUpdate=new Categories();
            categoriesDtoUpdate.setCategoryId(existingProduct.getCategories().getCategoryId());
            categoriesDtoUpdate.setName(existingProduct.getCategories().getName());
            productDtoCreated.setCategories(categoriesDtoUpdate);
            return productDtoCreated;
        }



    }

    public Boolean delete(UUID id) {
        Optional<Product> product=productRepository.findById(id);
        if(product.isPresent()){
            Product product1=product.get();
            product1.setDeleted(true);

            productRepository.save(product1);
            return true;
        }
        return false;
    }

    @Override
   public List<ProductDtoCustom> findByKeyword(String keyword,int pageNo,int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);

        List<ProductDtoCustom>productList=productRepository.search(keyword.toLowerCase(Locale.ROOT),pageable).getContent();
        if(!productList.isEmpty())
            return productList;

        return null;

    }

    @Override
    public List<ProductDtoCustom> getFilterByPrice(int minPrice, int maxPrice) {
        List<ProductDtoCustom>productList=productRepository.getFilterPrice(minPrice,maxPrice);
        return productList;
    }

    @Override
    public void deleteAllProduct() {
        productRepository.deleteAll();
    }

    @Override
    public List<BranDtoCustom> getByBrand(String brand) {
        List<BranDtoCustom>branDtoCustoms=productRepository.getByBrand(brand);
        return branDtoCustoms ;
    }

    @Override
    public List<CategoryDtoCustom> getByCategory(String category) {
        List<CategoryDtoCustom>categoryDtoCustoms=productRepository.getByCategory(category);
        return categoryDtoCustoms;
    }

    @Override
    public List<ProductDtoCustom> getAllProductBydate(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);
        List<ProductDtoCustom>productList=productRepository.findAllProductByDate(pageable).getContent();
        return productList;
    }


}





