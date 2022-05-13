package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "laptop")

public class Laptop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
