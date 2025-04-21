package com.example.vehicle_parking.frontend.Util;

import java.net.http.HttpClient;

public class HttpUtil {
    public static final HttpClient client = HttpClient.newHttpClient();
    public static final String BASE_URL = "http://localhost:8080/park/";
}
