package com.example.shonicserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@ToString
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
        @JsonIgnore
        @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Product> productList;

}
