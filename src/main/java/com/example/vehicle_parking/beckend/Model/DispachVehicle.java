package com.example.vehicle_parking.beckend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class DispachVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String ownerName;
    private String ownerPhone;

    @Column(name = "ref_no" , nullable = false)
    private Integer refNo;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    private boolean isAvailable;
    private Integer slotNo;
    private double totalPrice;
    private double numberOfHours;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
   @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;

}
