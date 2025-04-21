package com.example.vehicle_parking.beckend.Repo;

import com.example.vehicle_parking.beckend.Model.ArrivedVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArrivedRepo extends JpaRepository<ArrivedVehicle,Integer> {
    ArrivedVehicle findByRefNo(Integer refNo);

}
