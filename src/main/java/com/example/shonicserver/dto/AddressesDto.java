package com.example.shonicserver.dto;

import com.example.shonicserver.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressesDto {

    private UUID id;

    // users: one to one
    private User user;

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
