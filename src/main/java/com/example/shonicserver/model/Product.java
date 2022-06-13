package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "qty", nullable = false, length = 3)
    private Integer qty;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    // categoryId OneToMany
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Categories categories;

    // brandId ManyToOne
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // flashSale OneToMany
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private FlashSale flashSale;

    @Column(name = "image")
    private String image;

    @Column(name = "image_full")
    private Boolean imageFull;

    // rating OneToMan
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Rating rating;
}
