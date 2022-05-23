package com.example.shonicserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // users: one to one
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private Users users;

    // street
    @Column(name = "jalan", nullable = false)
    private String jalan;

    // Rt rw no
    @Column(name = "rtrwno", nullable = false)
    private String rtrwno;

    // kelurahan
    @Column(name = "kelurahan", nullable = false)
    private String kelurahan;

    // kecamatan
    @Column(name = "kecamatan", nullable = false)
    private String kecamatan;

    // kota / kabupaten
    @Column(name = "kota", nullable = false)
    private String kota;

    // kodepos
    @Column(name = "kodepos", nullable = false)
    private String kodepos;
}
