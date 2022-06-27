package com.example.shonicserver.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.shonicserver.dto.ProductDto;
import com.example.shonicserver.helper.ExcelHelper;
import com.example.shonicserver.model.User;
import com.example.shonicserver.payload.Response;
import com.example.shonicserver.payload.response.CreateProductResponse;
import com.example.shonicserver.service.ProductService;
import com.example.shonicserver.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/v1/owner")
@CrossOrigin(origins = "*")
@Api(tags = "Owner")
public class OwnerController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product/uploadProduct")
    public ResponseEntity<Response> uploadProductFile(@RequestParam("sheet_name") String sheet ,@RequestParam("file") MultipartFile file) {
        superadmin();
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product/imageUpload")
    public ResponseEntity<Object> getImageUrl(@RequestParam(value = "file") MultipartFile file) {
        String contentType = file.getContentType();
        if(!(contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg"))){
            return new ResponseEntity<>(new Response(400,"Pleas Upload Image File",null,null), HttpStatus.BAD_REQUEST);
        }
        if(file.getSize()>20000000){
            return new ResponseEntity<>(new Response(400,"Image too Large",null,null), HttpStatus.BAD_REQUEST);
        }

        try {
            //System.out.println(imporFile.getName());
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "shonic",
                    "api_key", "599463551394514",
                    "api_secret", "cEKvL2m5_V6cgkQUfIKddKe8v08"));
            Map response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            return new ResponseEntity<>(new Response(200,"Upload Success",response.get("url"),null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response(500,"Failed Upload File",null,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") UUID id){
        if( this.productService.delete(id)){
            return new ResponseEntity<>(new Response(200,"success",null,null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(400,"Bad Request",null,null), HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/product/deleteAll/{key}")
    public ResponseEntity<Response> deleteAll(@PathVariable("key") String key ){
        if(key.equals("sh0nic4") ){
            if(!superadmin()){
                productService.deleteAllProduct();
                return new ResponseEntity<>(new Response(200,"Success Delete All Product",null,null), HttpStatus.OK);
            }
            else
            return new ResponseEntity<>(new Response(403,"Forbidden Access",null,null), HttpStatus.FORBIDDEN);

        }else{
            return new ResponseEntity<>(new Response(400,"Key Invalid",null,null), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping ("/product/insertProduct")
    public ResponseEntity<Response> createNewProduct(@RequestBody ProductDto productDto){
        superadmin();
        try {
            CreateProductResponse productDtoInsert=this.productService.insert(productDto);
            return new ResponseEntity<>(new Response(201,"success",productDtoInsert,null), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new Response(500,"Fail create Product",null,null), HttpStatus.CREATED);
        }

    }

    @PutMapping("/user/updateadmin")
    public ResponseEntity<Response> addAdmin(@RequestParam(value = "user_email")String email){
        if(!superadmin())
            return new ResponseEntity<>(new Response(403,"You don't have access",null,null), HttpStatus.FORBIDDEN);
        Optional<User> userOptional= userService.findByEmail(email);
        if(userOptional.isPresent()){
            try {
                userService.makeAsAdmin(userOptional.get());
                return new ResponseEntity<>(new Response(200,"Success add "+email+" to admin",null,null), HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(new Response(201,"INTERNAL SERVER ERROR",null,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            return new ResponseEntity<>(new Response(400,"User Not Found",null,null), HttpStatus.BAD_REQUEST);
        }
    }

    private Boolean superadmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Email " + userDetails.getUsername());
        return userDetails.getUsername().equals("teamshonic@gmail.com");
    }
}
