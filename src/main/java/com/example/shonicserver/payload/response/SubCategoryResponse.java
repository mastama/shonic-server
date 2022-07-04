package com.example.shonicserver.payload.response;

import com.example.shonicserver.model.Categories;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubCategoryResponse {
    private Long id;
    private String name;

    public static SubCategoryResponse of(Categories data){
        return SubCategoryResponse.builder()
                .id(data.getCategoryId())
                .name(data.getName())
                .build();
    }
}
