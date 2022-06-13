package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "flashSale")
public class FlashSale {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "finish_time")
    private Timestamp finishTime;

    @Column(name = "flash_sale")
    private Integer flashSale;

    // foreign key
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;




}
