package com.example.vehicle_parking.frontend.Service;

import com.example.vehicle_parking.frontend.Model.ArrivedVehicle;
import com.example.vehicle_parking.frontend.Util.HttpUtil;
import com.example.vehicle_parking.frontend.Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArrivedVehicleService {

    private static final Gson gson = LocalDateTimeAdapter.getGsonInstance();
    public void getAll(Consumer <List<ArrivedVehicle>> callback) {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL +"arrVehicle"))
                .GET()
                .build();

        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response ->{
                    Type listType = new TypeToken<ArrayList<ArrivedVehicle>>() {}.getType();
                    List<ArrivedVehicle> arrivedVehicleList = gson.fromJson(response, listType);
                    Platform.runLater(() -> {
                        System.out.println("running");
                    });
                    callback.accept(arrivedVehicleList);

                }).exceptionally(e->{
                    e.printStackTrace();
                    return null;
                });

    }


    public void addArrivedVehicle(ArrivedVehicle arrivedVehicle, Consumer<Boolean> onResult) {
        String jsonBody = gson.toJson(arrivedVehicle);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL + "arrVehicle"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    System.out.println(response + " Created");
                    onResult.accept(true);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    onResult.accept(false);
                    return null;
                });
    }

    public void getLastRefNo(Consumer<Integer> callback) {
        getAll(arrivedVehicles -> {
            int lastRefNo = arrivedVehicles.stream()
                    .mapToInt(ArrivedVehicle::getRefNo)
                    .max()
                    .orElse(0); // If no data, return 0

            callback.accept(lastRefNo);
        });
    }

    public void updateArrivedVehicle(ArrivedVehicle arrivedVehicle) {
        String jsonBody = gson.toJson(arrivedVehicle);

        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL+"arrVehicle/"+arrivedVehicle.getId()))
                .header("Content-Type","application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    System.out.println(jsonBody + "Updated");
                    System.out.println(response);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                      return null;
                });

    }
}
