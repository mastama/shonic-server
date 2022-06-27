package com.example.shonicserver.model;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name",  unique = true)
    private String name;

    @Column(name = "price" ,nullable = false)
    private Integer price;

    @Column(name = "qty",  length = 3)
    private Integer qty;

    @Column(name = "description",length = 5000)
    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer discount;
    // categoryId OneToMany
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "categories")
    private Categories categories;

    // brandId ManyToOne
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "brand")
    private Brand brand;

    // discountId OneToMany
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FlashSale> flashsale;

    @Column(name = "image")
    private String image;

    @Column(name = "image_full")
    private String imageFull;

    @Column(nullable = false)
    private Float weight;

    @Column(columnDefinition = "integer default 0")
    private Float rating;

    @Column(columnDefinition = "integer default 0")
    private Integer review;

    // rating OneToMany
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratingList;



}
