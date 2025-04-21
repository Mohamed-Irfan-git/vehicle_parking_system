package com.example.vehicle_parking.beckend.Service;


import com.example.vehicle_parking.beckend.Model.ArrivedVehicle;

import com.example.vehicle_parking.beckend.Repo.ArrivedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrivedVehicleService {

    @Autowired
    private ArrivedRepo arrivedRepo;

    public List<ArrivedVehicle> findAll() {
        return arrivedRepo.findAll();
    }

    public ArrivedVehicle findById(int id) {
        return arrivedRepo.findById(id).get();
    }

    public ArrivedVehicle findByRefNo(Integer refNo){
        return arrivedRepo.findByRefNo(refNo);
    }

    public void save(ArrivedVehicle arrivedVehicle) {
        arrivedRepo.save(arrivedVehicle);
    }

    public void deleteById(Integer id) {
        arrivedRepo.deleteById(id);
    }

    public void update(ArrivedVehicle arrivedVehicle) {
        arrivedRepo.save(arrivedVehicle);
    }
}
