package com.example.vehicle_parking.beckend.Service;


import com.example.vehicle_parking.beckend.Model.Vehicle;
import com.example.vehicle_parking.beckend.Repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;


    public List<Vehicle> findAll() {
        return vehicleRepo.findAll();
    }
    public List<Vehicle> findByName(String name) {
        return vehicleRepo.findByNameContainingIgnoreCase(name);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepo.save(vehicle);
    }

    public void deleteVehicleById(int id) {
        vehicleRepo.deleteById(id);
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleRepo.save(vehicle);
    }


}
