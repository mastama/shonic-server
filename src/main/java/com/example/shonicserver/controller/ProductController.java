package com.example.shonicserver.controller;

import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.dto.ProductDtoCustom;
import com.example.shonicserver.model.Product;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.response.CreateProductResponse;
import com.example.shonicserver.repository.ProductRepository;
import com.example.shonicserver.service.ProductService;
import io.swagger.annotations.Api;
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



    //softdelete

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
