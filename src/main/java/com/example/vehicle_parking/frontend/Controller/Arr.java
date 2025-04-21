package com.example.vehicle_parking.frontend.Controller;

import com.example.vehicle_parking.frontend.Model.ArrivedVehicle;
import com.example.vehicle_parking.frontend.Model.DepatureVehicle;
import com.example.vehicle_parking.frontend.Service.ArrivedVehicleService;
import com.example.vehicle_parking.frontend.Service.DispatchService;

public class Arr {

    public void updateArrivedVehicle(ArrivedVehicle arrivedVehicle) {
        ArrivedVehicleService arrivedVehicleService = new ArrivedVehicleService();
        arrivedVehicleService.updateArrivedVehicle(arrivedVehicle);
        int a = arrivedVehicle.getSlotNo();


    }

    public void addDepVehicle(DepatureVehicle depatureVehicle) {
        DispatchService dispatchService = new DispatchService();
        dispatchService.addDispatch(depatureVehicle);


    }




}
