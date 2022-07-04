package com.example.shonicserver.payload.response;

import com.example.shonicserver.model.Categories;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    @JsonProperty("sub_category")
    private List<SubCategoryResponse> subCategories;

}

