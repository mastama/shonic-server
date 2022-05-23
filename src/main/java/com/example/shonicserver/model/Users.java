package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // username
    @Column(name = "username", nullable = false)
    private String username;

    // email unique
    @Column(name = "email", nullable = false)
    private String email;

    // password
    @Column(name = "password", nullable = false)
    private String password;

    // role
    @Column(name = "role", nullable = false)
    private String role;

    // addresses one to one
    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private Addresses addresses;
}
