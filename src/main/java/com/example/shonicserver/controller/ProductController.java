package com.example.shonicserver.controller;


import com.example.shonicserver.dto.ProductDetailDTO;
import com.example.shonicserver.dto.ProductListDTO;
import com.example.shonicserver.dto.BrandDtoCustom;
import com.example.shonicserver.dto.CategoryDtoCustom;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.repository.ProductRepository;
import com.example.shonicserver.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

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

      ProductDetailDTO product=this.productService.getById(id);
      if(product!=null){
          return new ResponseEntity<>(new Response(200,"succsess",product,null),HttpStatus.OK);
      }else return new ResponseEntity<>(new Response(400,"Product ID Not Found",null,null),HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/getByBrand")
    public ResponseEntity<Response>getByBrand(@RequestParam String brand){
        List<BrandDtoCustom>branDtoCustom=this.productService.getByBrand(brand);
        return new ResponseEntity<>(new Response(200,"succsess",branDtoCustom,null),HttpStatus.OK);
    }
    @GetMapping("/getCategory")
    public ResponseEntity<Response>getByCategory(@RequestParam String category){
        List<CategoryDtoCustom>categoryDtoCustoms=this.productService.getByCategory(category);
        return new ResponseEntity<>(new Response(200,"succsess",categoryDtoCustoms,null),HttpStatus.OK);

    }

    @GetMapping("/getAllLatestProduct")
    public ResponseEntity<Response> getLatestProduct(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        List<ProductListDTO> listProducts = productService.getNewestProduct(pageNo,pageSize);
        return new ResponseEntity<>(new Response(200,"success",listProducts,null),HttpStatus.OK);

    }

    @GetMapping("/simmilar/{product_id}")
    public ResponseEntity<Response> getSimilarProduct(@PathVariable(value = "product_id") UUID productId){
        try {
            List<ProductListDTO> productList = productService.getSimmilarityProduct(productId);
            return new ResponseEntity<>(new Response(200,"success",productList,null),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(400,"Product ID Not Found",null,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/search")
    public ResponseEntity<Response> searchByKeyword(@RequestParam(value = "keyword") String keyword,
                                                 @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                    @RequestParam (value="filter_minPrice",required = false,defaultValue = "0")Integer minPrice,
                                                    @RequestParam(value = "filter_maxPrice",required = false) Integer maxPrice,
                                                    @RequestParam(value = "filter_4Star",required = false,defaultValue = "false") Boolean is4StarRating,
                                                    @RequestParam(value = "filter_onlyDiscount",required = false,defaultValue = "false") Boolean onlyDiscount,
                                                    @RequestParam(value = "sort_dateDesc",required = false,defaultValue = "false")Boolean sortbyDate,
                                                    @RequestParam(value = "sort_priceDesc",required = false,defaultValue = "false")Boolean sortbyPriceDesc,
                                                    @RequestParam(value = "sort_priceAsc",required = false,defaultValue = "false")Boolean sortbyPriceAsc
                                                    ) {

        if(pageNo <= 0 ||pageSize <= 0 || keyword == null){
            return new ResponseEntity<>(new Response(400, "Invalid Params", null, null), HttpStatus.BAD_REQUEST);
        }

        if(minPrice==null){
            minPrice=0;
        }
        if(maxPrice==null){
            maxPrice=1000000000;
        }

        float rating ;
        if(is4StarRating==null || !is4StarRating){
            rating = (float) 0;
        }else {
            rating = 4F;
        }

        int discount= 0;
        if(onlyDiscount){
            discount = 1;
        }

        String sort = "relevan";
        if((sortbyPriceDesc && sortbyPriceAsc)|| (sortbyPriceDesc && sortbyDate) || (sortbyDate && sortbyPriceAsc) ){
            sort = "relevan";
        } else if (sortbyDate) {
            sort = "date";
        } else if (sortbyPriceAsc) {
            sort = "asc";
        } else if (sortbyPriceDesc) {
            sort = "desc";
        }

        List<ProductListDTO> listProducts = productService.findByKeyword(keyword, pageNo, pageSize,minPrice,maxPrice, rating,discount,sort);
        Map<String,Object> result= new HashMap<>();

        if(listProducts!=null ||  (listProducts ==null && pageNo>1)){
            result.put("found",true);
            result.put("product",listProducts);
        }
        else{
            listProducts = productService.getNewestProduct(pageNo,pageSize);
            result.put("found",false);
            result.put("product",listProducts);
        }

        return new ResponseEntity<>(new Response(200, "success", result, null), HttpStatus.OK);
    }

    @GetMapping("/getAllByCategory")
    public ResponseEntity<Response> getProductByCategories(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                           @RequestParam("category_name")String categoryName,
                                                           @RequestParam (value="filter_minPrice",required = false,defaultValue = "0")Integer minPrice,
                                                           @RequestParam(value = "filter_maxPrice",required = false) Integer maxPrice,
                                                           @RequestParam(value = "filter_4Star",required = false,defaultValue = "false") Boolean is4StarRating,
                                                           @RequestParam(value = "filter_onlyDiscount",required = false,defaultValue = "false") Boolean onlyDiscount,
                                                           @RequestParam(value = "sort_dateDesc",required = false,defaultValue = "false")Boolean sortbyDate,
                                                           @RequestParam(value = "sort_priceDesc",required = false,defaultValue = "false")Boolean sortbyPriceDesc,
                                                           @RequestParam(value = "sort_priceAsc",required = false,defaultValue = "false")Boolean sortbyPriceAsc){

        if(pageNo <= 0 ||pageSize <= 0){
            return new ResponseEntity<>(new Response(400, "Invalid Params", null, null), HttpStatus.BAD_REQUEST);
        }

        if(minPrice==null){
            minPrice=0;
        }
        if(maxPrice==null){
            maxPrice=1000000000;
        }

        float rating ;
        if(is4StarRating==null || !is4StarRating){
            rating = (float) 0;
        }else {
            rating = 4F;
        }

        int discount= 0;
        if(onlyDiscount){
            discount = 1;
        }

        String sort = "relevan";
        if((sortbyPriceDesc && sortbyPriceAsc)|| (sortbyPriceDesc && sortbyDate) || (sortbyDate && sortbyPriceAsc) ){
            sort = "relevan";
        } else if (sortbyDate) {
            sort = "date";
        } else if (sortbyPriceAsc) {
            sort = "asc";
        } else if (sortbyPriceDesc) {
            sort = "desc";
        }
        List<ProductListDTO> productList = productService.getProductCategories(pageNo,pageSize,categoryName,minPrice,maxPrice,rating,discount,sort);


        return new ResponseEntity<>(new Response(200, "success", productList, null), HttpStatus.OK);
    }

    @GetMapping("/getAllByBrand")
    public ResponseEntity<Response> getProductByBrand(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                           @RequestParam("brand_name")String brandName,
                                                           @RequestParam (value="filter_minPrice",required = false,defaultValue = "0")Integer minPrice,
                                                           @RequestParam(value = "filter_maxPrice",required = false) Integer maxPrice,
                                                           @RequestParam(value = "filter_4Star",required = false,defaultValue = "false") Boolean is4StarRating,
                                                           @RequestParam(value = "filter_onlyDiscount",required = false,defaultValue = "false") Boolean onlyDiscount,
                                                           @RequestParam(value = "sort_dateDesc",required = false,defaultValue = "false")Boolean sortbyDate,
                                                           @RequestParam(value = "sort_priceDesc",required = false,defaultValue = "false")Boolean sortbyPriceDesc,
                                                           @RequestParam(value = "sort_priceAsc",required = false,defaultValue = "false")Boolean sortbyPriceAsc){

        if(pageNo <= 0 ||pageSize <= 0){
            return new ResponseEntity<>(new Response(400, "Invalid Params", null, null), HttpStatus.BAD_REQUEST);
        }

        if(minPrice==null){
            minPrice=0;
        }
        if(maxPrice==null){
            maxPrice=1000000000;
        }

        float rating ;
        if(is4StarRating==null || !is4StarRating){
            rating = (float) 0;
        }else {
            rating = 4F;
        }

        int discount= 0;
        if(onlyDiscount){
            discount = 1;
        }

        String sort = "relevan";
        if((sortbyPriceDesc && sortbyPriceAsc)|| (sortbyPriceDesc && sortbyDate) || (sortbyDate && sortbyPriceAsc) ){
            sort = "relevan";
        } else if (sortbyDate) {
            sort = "date";
        } else if (sortbyPriceAsc) {
            sort = "asc";
        } else if (sortbyPriceDesc) {
            sort = "desc";
        }
        List<ProductListDTO> productList = productService.getProductBrand(pageNo,pageSize,brandName,minPrice,maxPrice,rating,discount,sort);

        return new ResponseEntity<>(new Response(200, "success", productList, null), HttpStatus.OK);
    }
}
