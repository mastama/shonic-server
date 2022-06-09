package com.example.shonicserver.dto;

import com.example.shonicserver.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UlasanDto {

    private Integer id;
    private Integer ulasan;
    private Rating rating;
}
