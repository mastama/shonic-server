package com.example.shonicserver.controller;

import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.mock.ProductMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.json.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/mock")
@Api(tags = "Data Dummy")
public class DummyController {

    @GetMapping("/product")
    public ResponseEntity<Response> getAllProduct(){
        String url = "https://62adc82db735b6d16a39b83d.mockapi.io/api/v1/product";
        RestTemplate restTemplate = new RestTemplate();
        List result = restTemplate.getForObject(url,List.class);
        ArrayList<ProductMock> responseData= new ArrayList<>();

        String[] brandArray = {"Asus","Samsung","Xiaomi"};
        String[] categoryArray = {"Laptop","HP","TV"};

        ArrayList<Object> brands=new ArrayList<>();
        ArrayList<Object> categories=new ArrayList<>();

        for(int i=0;i<brandArray.length;i++){
            LinkedHashMap<String, Object> category =new LinkedHashMap<>();
            category.put("id",String.valueOf(i));
            category.put("name",categoryArray[i]);
            categories.add(category);
            LinkedHashMap<String, Object> brand=new LinkedHashMap<>();
            brand.put("id",String.valueOf(i));
            brand.put("name",brandArray[i]);
            brands.add(brand);
        }

        for(int i=0;i<result.size();i++){
//            ProductMock item = result.get(i);
            ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            ProductMock item = mapper.convertValue(result.get(i),ProductMock.class);
            Random r = new Random();
            int discount = r.nextInt(100-1) + 1;
            double rating =(Math.random() * ((5.0 - 0.0) + 0.1));
            item.setDiscount(String.valueOf(discount).concat("%"));
            DecimalFormat df = new DecimalFormat("#.##");

            String priceString  = String.valueOf((int)(Math.random() * ((1000 - 20) + 20)));
            int price = Integer.parseInt(priceString.concat("000"));

            item.setRating(Float.valueOf(df.format(rating)));
            item.setPrice(price);

            item.setBrand((Map<String, Object>) brands.get(r.nextInt((brands.size())-0) + 0));
            item.setCategories((Map<String, Object>) categories.get(r.nextInt((brands.size()) - 0)));
            responseData.add(item);

        }
        return new ResponseEntity<>(new Response(200,"Succes",responseData,null), HttpStatus.OK);

    }
}
