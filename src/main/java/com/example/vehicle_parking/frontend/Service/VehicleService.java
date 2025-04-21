package com.example.vehicle_parking.frontend.Service;

import com.example.vehicle_parking.frontend.Model.Vehicle;
import com.example.vehicle_parking.frontend.Util.HttpUtil;
import com.example.vehicle_parking.frontend.Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class VehicleService {

    private static final Gson gson = LocalDateTimeAdapter.getGsonInstance();

    public void getAllVehicles(Consumer<List<Vehicle>> callback) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL + "vehicles"))
                .GET()
                .build();

        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    Type listType = new TypeToken<ArrayList<Vehicle>>() {}.getType();
                    List<Vehicle> vehicles = gson.fromJson(response, listType);
                    callback.accept(vehicles);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    public void addVehicle(Vehicle vehicle,Runnable onSuccess) {
        String body = gson.toJson(vehicle);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL + "vehicle"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    System.out.println(response.body() + " created");
                    Platform.runLater(onSuccess);


                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    public void UpdateVehicle(Vehicle vehicle) {
        String body = gson.toJson(vehicle);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL + "vehicle"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    System.out.println(response.body() + " updated");
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    public void findByName(String name, ListView<Vehicle> listView) {
        try {
            URI uri = URI.create(HttpUtil.BASE_URL + "vehicle/" + URLEncoder.encode(name, StandardCharsets.UTF_8));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        Type listType = new TypeToken<ArrayList<Vehicle>>() {}.getType();
                        List<Vehicle> vehicles = gson.fromJson(response, listType);

                        List<Vehicle> partialMatches = vehicles.stream()
                                .filter(v -> v.getName().toLowerCase().contains(name.toLowerCase()))
                                .collect(Collectors.toList());

                        Platform.runLater(() -> {
                            if (!partialMatches.isEmpty()) {
                                listView.getItems().setAll(partialMatches);
                                listView.setVisible(true);

                                double rowHeight = 24;
                                int maxVisibleRows = 5;
                                int itemCount = partialMatches.size();

                                listView.setPrefHeight(Math.min(itemCount, maxVisibleRows) * rowHeight + 2);
                            } else {
                                listView.getItems().clear();
                                listView.setVisible(false);
                            }
                        });
                    })
                    .exceptionally(e -> {
                        e.printStackTrace();
                        Platform.runLater(() -> listView.setVisible(false));
                        return null;
                    });
        } catch (Exception e) {
            e.printStackTrace();
            listView.setVisible(false);
        }
    }
}
