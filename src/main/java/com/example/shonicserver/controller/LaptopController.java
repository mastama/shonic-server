package com.example.shonicserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/laptop")
public class LaptopController {

  @GetMapping
  public String getLaptop() {
    return "Berhasil mengambil laptop";
  }
}
