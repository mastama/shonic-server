package com.example.shonicserver.controller;

import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.helper.ExcelHelper;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@CrossOrigin(origins = "*")
@Api(tags = "Owner")
public class OwnerController {
    @Autowired
    private ProductService productService;

    @PostMapping("/uploadProduct")
    public ResponseEntity<Response> uploadProductFile(@RequestParam("sheet_name") String sheet ,@RequestParam("file") MultipartFile file) {
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<ProductDto> productDtoList = new ArrayList<>();
                productDtoList = ExcelHelper.excelProduct(file.getInputStream(),sheet);
                if(!productDtoList.isEmpty()){
                    for (ProductDto product: productDtoList) {
                        productService.insert(product);
                    }
                    message = "Uploaded the file successfully: " + file.getOriginalFilename();
                    return new ResponseEntity<>(new Response(200,message,null,null), HttpStatus.OK);
                }
                else{
                    message= "File Empty";
                    return new ResponseEntity<>(new Response(200,message,null,null), HttpStatus.OK);
                }


            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return new ResponseEntity<>(new Response(500,e.getMessage(),null,null), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(new Response(400,message,null,null), HttpStatus.BAD_REQUEST);

    }
}
