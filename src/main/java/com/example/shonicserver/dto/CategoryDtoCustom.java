package com.example.shonicserver.dto;

import com.example.shonicserver.model.CategoryParent;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDtoCustom {
    private Long categoryId;
    private String name;
    private Long category_parent;

}
