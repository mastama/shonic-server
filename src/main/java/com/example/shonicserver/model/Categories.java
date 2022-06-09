package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "categories")
public class Categories {

        @Id
        @Column(name = "category_id", nullable = false)
        @GeneratedValue(strategy = GenerationType.AUTO)
        private String categoryId;

        @Column(name = "name", nullable = false, unique = true)
        private String name;

        // foreign key
        @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        private Product product;

}
