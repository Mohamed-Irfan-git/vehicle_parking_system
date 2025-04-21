package com.example.vehicle_parking.frontend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepatureVehicle {

    String model;
    String ownerName;
    String ownerPhone;
    Integer refNo;
    LocalDateTime arrivalTime;
    boolean isAvailable;
    Integer slotNo;
    LocalDateTime departureTime;
    double totalPrice;
    double numberOfHours;

    private User user;

    private Vehicle vehicle;


}
