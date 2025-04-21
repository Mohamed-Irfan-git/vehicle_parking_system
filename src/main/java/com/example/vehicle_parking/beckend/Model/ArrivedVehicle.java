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
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"vehicle_id", "arrival_time"}
        ))
@Data
@Builder
public class ArrivedVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String model;
    String ownerName;
    String ownerPhone;
    @Column(unique = true)
    Integer refNo;
    LocalDateTime arrivalTime;
    @Column(name= "is_available")
    boolean isAvailable;
    Integer slotNo;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
