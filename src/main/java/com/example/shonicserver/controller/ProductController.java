package com.example.shonicserver.controller;

import com.example.shonicserver.dto.BranDtoCustom;
import com.example.shonicserver.dto.CategoryDtoCustom;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.response.CreateProductResponse;
import com.example.shonicserver.repository.ProductRepository;
import com.example.shonicserver.service.ProductService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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




    @GetMapping("getById/{id}")
    public ResponseEntity<Response> getOneProduct(@PathVariable UUID id) {
      Product product=this.productService.getById(id);
        return new ResponseEntity<>(new Response(200,"succsess",product,null),HttpStatus.OK);
    }
//    @GetMapping("/filter")
//    public  ResponseEntity<Response> getFilterProduct(@RequestParam int minPrice,@RequestParam int maxPrice){
//        List<ProductDtoCustom> listProducts= this.productService.getFilterByPrice(minPrice,maxPrice);
//        return new ResponseEntity<>(new Response(200,"success",listProducts,null),HttpStatus.OK);
//    }

    @GetMapping("/getByBrad")
    public ResponseEntity<Response>getByBrand(@RequestParam String brand){
        List<BranDtoCustom>branDtoCustom=this.productService.getByBrand(brand);
        return new ResponseEntity<>(new Response(200,"succsess",branDtoCustom,null),HttpStatus.OK);
    }
    @GetMapping("/getCategory")
    public ResponseEntity<Response>getByCategory(@RequestParam String category){
        List<CategoryDtoCustom>categoryDtoCustoms=this.productService.getByCategory(category);
        return new ResponseEntity<>(new Response(200,"succsess",categoryDtoCustoms,null),HttpStatus.OK);
    }

    //softdelete

    @GetMapping("/search")
    public ResponseEntity<Response> searchByKeyword(@RequestParam(value = "keyword") String keyword,
                                                 @RequestParam int pageNo,@RequestParam int pageSize) {

        if(pageNo <= 0 ||pageSize <= 0 || keyword == null){
            return new ResponseEntity<>(new Response(400, "Invalid Params", null, null), HttpStatus.BAD_REQUEST);
        }
        String nullValue = RandomStringUtils.randomAlphanumeric(10);

            List<ProductDtoCustom> listProducts = productService.findByKeyword(keyword, pageNo, pageSize);
            Map<String,Object> result= new HashMap<>();

            if(listProducts!=null){
                result.put("found",true);
                result.put("product",listProducts);
            }
            else{
                listProducts = productRepository.getProductByDate();
                result.put("found",false);
                result.put("product",listProducts);
            }

        return new ResponseEntity<>(new Response(200, "success", result, null), HttpStatus.OK);


    }

}
