package com.example.vehicle_parking.frontend.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {

    Integer id;
    String name;
    double pricePerHour;

    private List<ArrivedVehicle> arrivedVehicle;

    public Vehicle(String name, double pricePerHour) {
        this.name = name;
        this.pricePerHour = pricePerHour;
    }





}
