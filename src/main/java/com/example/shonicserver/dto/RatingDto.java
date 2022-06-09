package com.example.shonicserver.dto;

import com.example.shonicserver.model.Product;
import com.example.shonicserver.model.Ulasan;
import com.example.shonicserver.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private UUID id;
    private Integer rating;
    private User user;
    private Product product;
    private List<Ulasan> ulasan;
}
