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
        @GeneratedValue(strategy = GenerationType.AUTO)
        private String categoryId;

        @Column(name = "name", nullable = false, unique = true)
        private String name;

        // foreign key
        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categories")
        private List<Product> product;


}
