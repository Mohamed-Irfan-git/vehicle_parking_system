package com.example.vehicle_parking.beckend.Controller;


import com.example.vehicle_parking.beckend.Model.ArrivedVehicle;
import com.example.vehicle_parking.beckend.Service.ArrivedVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/park")
public class ArrivedVehicleController {

    @Autowired
    private ArrivedVehicleService arrivedVehicleService;

    @GetMapping("/arrVehicle")
    public List<ArrivedVehicle> findAll(){
        return arrivedVehicleService.findAll();
    }

    @GetMapping("/arrVehicle/{id}")
    public ArrivedVehicle findById(@PathVariable("id") Integer id){
        return arrivedVehicleService.findById(id);
    }


    @GetMapping("/arrVehicle/ref/{refNo}")
    public ArrivedVehicle findByRefNo(@PathVariable("refNo") Integer refNo){
        return arrivedVehicleService.findByRefNo(refNo);
    }

    @PostMapping("/arrVehicle")
    public void save(@RequestBody ArrivedVehicle arrivedVehicle) {
        arrivedVehicleService.save(arrivedVehicle);
    }

    @DeleteMapping("/arrVehicle/{id}")
    public void delete(@PathVariable("id") Integer id) {
        arrivedVehicleService.deleteById(id);
    }
    @PutMapping("/arrVehicle/{id}")
    public void update(@PathVariable Integer id, @RequestBody ArrivedVehicle arrivedVehicle) {
        arrivedVehicle.setId(id); // ensure the ID is set
        arrivedVehicleService.update(arrivedVehicle);
    }


}
