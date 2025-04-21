package com.example.vehicle_parking.beckend.Repo;

import com.example.vehicle_parking.beckend.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByNameContainingIgnoreCase(String name);

}
