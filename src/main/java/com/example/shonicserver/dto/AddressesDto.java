package com.example.shonicserver.dto;

import com.example.shonicserver.model.Users;
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
public class AddressesDto {

    private UUID id;

    // users: one to one
    private Users users;

    // street
    private String jalan;

    // Rt rw no
    private String rtrwno;

    // kelurahan
    private String kelurahan;

    // kecamatan
    private String kecamatan;

    // kota / kabupaten
    private String kota;

    // kodepos
    private String kodepos;
}
