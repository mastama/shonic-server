package com.example.shonicserver.dto;

import com.example.shonicserver.model.Categories;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CategoryParentDTO {
    private Long id;
    private String name;
    @JsonProperty("sub_categories")
    private List<Categories> subCategories;


}
