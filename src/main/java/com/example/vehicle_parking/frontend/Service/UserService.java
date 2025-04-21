package com.example.vehicle_parking.frontend.Service;

import com.example.vehicle_parking.frontend.Model.User;
import com.example.vehicle_parking.frontend.Util.HttpUtil;
import com.example.vehicle_parking.frontend.Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;


public class UserService {
    private static final Gson gson = LocalDateTimeAdapter.getGsonInstance();

    public void getAllUsers(Consumer<List<User>> callback) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL+"users"))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();


        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response ->{
                    Type listType = new TypeToken<List<User>>() {}.getType();
                    List<User> users = gson.fromJson(response, listType);
                    callback.accept(users);
                })
                .exceptionally(e->
                {
                    e.printStackTrace();
                    return null;
                });
    }

    public void addUser(User user) {
        String body = gson.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpUtil.BASE_URL+"user"))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpUtil.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response ->{
                    System.out.println("Created"+response.body());
                })
                .exceptionally(e->{
                    e.printStackTrace();
                    return null;
                });
    }
}
