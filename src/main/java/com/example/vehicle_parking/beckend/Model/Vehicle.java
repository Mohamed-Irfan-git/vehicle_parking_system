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
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    Double pricePerHour;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<ArrivedVehicle> arrivedVehicle = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<DispachVehicle> dispachVehicleList = new ArrayList<>();






}
