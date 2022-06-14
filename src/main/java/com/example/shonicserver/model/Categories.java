package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Categories {

        @Id
        @Column(name = "category_id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long categoryId;

        @Column(name = "name", unique = true)
        private String name;

        // foreign key
        @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Product> productList;

}
