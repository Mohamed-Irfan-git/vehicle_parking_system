package com.example.vehicle_parking.beckend.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    @Column(nullable = false)
    String firstName;

    String lastName;
    String email;

    @Column(unique = true , nullable = false)
    String password;

    String phone;

    String address;

    String nic;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ArrivedVehicle> arrivedVehicleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<DispachVehicle> dispachVehicleList = new ArrayList<>();

}
