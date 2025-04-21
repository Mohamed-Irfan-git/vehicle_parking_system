package com.example.vehicle_parking.beckend.Controller;

import com.example.vehicle_parking.beckend.Model.DispachVehicle;
import com.example.vehicle_parking.beckend.Service.DispatchVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/park")
public class DispatchController {

    @Autowired
    private DispatchVehicleService dispatchVehicleService;


    @GetMapping("/dispatchVehicles")
    public List<DispachVehicle> findAll() {
        return dispatchVehicleService.findAll();
    }

    @GetMapping("/dispatchVehicle/{id}")
    public DispachVehicle findById(@PathVariable("id") int id) {
        return dispatchVehicleService.findById(id);
    }

    @PostMapping("/dispatchVehicle")
    public void save(@RequestBody DispachVehicle dispachVehicle) {
        dispatchVehicleService.save(dispachVehicle);
    }

    @DeleteMapping("/dispatchVehicle/{id}")
    public void deleteById(@PathVariable("id") int id) {
        dispatchVehicleService.deleteById(id);
    }

    @PutMapping("/dispatchVehicle")
    public void update(@RequestBody DispachVehicle dispachVehicle) {
        dispatchVehicleService.update(dispachVehicle);
    }



}
