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
import java.text.DecimalFormat;
import java.time.LocalDateTime;
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

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "qty",  length = 3)
    private Integer qty;

    @Column(name = "description",length = 5000)
    private String description;

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
    private Float rating;
    private Integer review;

    // rating OneToMany
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratingList;

    @PrePersist
    protected void prePersistProduct() {
        float rating = (float) ((Math.random() * (5.0 - 3.0)) + 3.0);
        DecimalFormat df = new DecimalFormat("#.##");
        this.review = (int) ((Math.random() * (100)) + 1);
        this.rating = Float.valueOf(df.format(rating));
    }

}
