package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "flashSale")
public class FlashSale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "finish_time")
    private Timestamp finishTime;

    @Column(name = "discount")
    private Integer discount;

    // foreign key
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "flashSale")
    private List<Product> product;




}
