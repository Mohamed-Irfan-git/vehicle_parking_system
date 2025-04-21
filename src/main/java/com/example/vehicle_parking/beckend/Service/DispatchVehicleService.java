package com.example.vehicle_parking.beckend.Service;

import com.example.vehicle_parking.beckend.Model.DispachVehicle;
import com.example.vehicle_parking.beckend.Repo.DispatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispatchVehicleService {

    @Autowired
    private DispatchRepo dispatchRepo;

    public List<DispachVehicle> findAll() {
        return dispatchRepo.findAll();
    }

    public DispachVehicle findById(int id) {
        return dispatchRepo.findById(id).get();
    }

    public void save(DispachVehicle dispachVehicle) {
        dispatchRepo.save(dispachVehicle);
    }
    public void deleteById(int id) {
        dispatchRepo.deleteById(id);
    }

    public void update(DispachVehicle dispachVehicle) {
        dispatchRepo.save(dispachVehicle);
    }


}
