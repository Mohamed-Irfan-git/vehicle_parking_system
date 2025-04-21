package com.example.vehicle_parking.frontend.Model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrivedVehicle {
    Integer id;
    String model;
    String ownerName;
    String ownerPhone;
    Integer refNo;
    LocalDateTime arrivalTime;
    boolean isAvailable;
    Integer slotNo;


    private Vehicle vehicle;
    private User user;



}
