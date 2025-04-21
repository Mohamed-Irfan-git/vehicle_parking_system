package com.example.vehicle_parking.beckend.Repo;

import com.example.vehicle_parking.beckend.Model.DispachVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchRepo extends JpaRepository<DispachVehicle,Integer> {
}
