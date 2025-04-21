package com.example.vehicle_parking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JavaFxApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/login.fxml"));
        AnchorPane root = fxmlLoader.load();

        primaryStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Vehicle Parking System");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
