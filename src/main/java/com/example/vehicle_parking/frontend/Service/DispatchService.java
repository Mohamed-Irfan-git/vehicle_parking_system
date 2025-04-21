package com.example.vehicle_parking.frontend.Service;

import com.example.vehicle_parking.frontend.Model.ArrivedVehicle;
import com.example.vehicle_parking.frontend.Model.DepatureVehicle;
import com.example.vehicle_parking.frontend.Util.HttpUtil;
import com.example.vehicle_parking.frontend.Util.LocalDateTimeAdapter;
import com.google.gson.Gson;




import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;


public class DispatchService {

    private static final Gson gson = LocalDateTimeAdapter.getGsonInstance();
    public void getByRef(Consumer <ArrivedVehicle> callback,Integer ref) {
        System.out.println("Looking for vehicle with ref: " + ref);
       HttpRequest request = HttpRequest.newBuilder().GET()
               .uri(URI.create(HttpUtil.BASE_URL+"arrVehicle/ref/"+ref))
               .GET()
               .build();

       HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
               .thenApply(HttpResponse::body)
               .thenAccept(response -> {
                   ArrivedVehicle arrivedVehicle = gson.fromJson(response, ArrivedVehicle.class);
                   System.out.println(response);
                   callback.accept(arrivedVehicle);
               })
               .exceptionally(e->{
                   e.printStackTrace();
                   return null;
               });
   }

   public void addDispatch(DepatureVehicle depatureVehicle) {
       String jsonBody = gson.toJson(depatureVehicle);

       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(HttpUtil.BASE_URL+"dispatchVehicle"))
               .header("Content-Type", "application/json")
               .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
               .build();

       HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
               .thenApply(HttpResponse::body)
               .thenAccept(response -> {
                   System.out.println(jsonBody + "Created");
                   System.out.println(response);
               })
               .exceptionally(e -> {
                   e.printStackTrace();
                   return null;
               });
   }


}
