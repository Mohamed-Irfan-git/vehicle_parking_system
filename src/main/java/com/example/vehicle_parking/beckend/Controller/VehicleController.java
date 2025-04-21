package com.example.vehicle_parking.beckend.Controller;

import com.example.vehicle_parking.beckend.Model.Vehicle;
import com.example.vehicle_parking.beckend.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/park")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public List<Vehicle> findAll(){
        return vehicleService.findAll();
    }

    @GetMapping("/vehicle/{name}")
    public List<Vehicle> findByName(@PathVariable("name") String name){
        return vehicleService.findByName(name);
    }


    @PostMapping("/vehicle")
    public void save(@RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
    }

    @DeleteMapping("/vehicle/{id}")
    public void deleteVehicleById(@PathVariable("id") int id) {
        vehicleService.deleteVehicleById(id);
    }

    @PutMapping("/vahicle")
    public void updateVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(vehicle);
    }
}
