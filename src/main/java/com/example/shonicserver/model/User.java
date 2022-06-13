package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // username as email uniq
    @Column(name = "email", nullable = false)
    private String username;

    // email unique
   /* @Column(name = "email", nullable = false)
    private String email;*/

    // password
    @Column(name = "password", nullable = false)
    private String password;
    //full name
    @Column(name = "full_name")
    private String fullname;

    // addresses one to one
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Addresses addresses;
    // role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // rating
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Rating rating;


    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Addresses getAddresses() {
        return addresses;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

