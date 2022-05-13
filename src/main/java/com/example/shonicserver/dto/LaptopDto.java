package com.example.shonicserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LaptopDto {
  private Long id;
  private String name;
  private String description;
  private Double price;
}
