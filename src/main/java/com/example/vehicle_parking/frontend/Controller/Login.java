package com.example.vehicle_parking.frontend.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import com.example.vehicle_parking.frontend.Model.User;
import com.example.vehicle_parking.frontend.Service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField username;


    @FXML
    private Button login;

    @FXML
    private PasswordField password;



    @FXML
    void initialize() {



     username.setOnKeyPressed(event -> {
         if (event.getCode() == KeyCode.ENTER) {
             password.requestFocus();
         }
     });

     password.setOnKeyPressed(event -> {
         if (event.getCode() == KeyCode.ENTER) {
             login.requestFocus();
             login();
         }
     });

    login.setOnAction(event -> {
        login();
    });


    }

    private void login() {
        UserService userService = new UserService();

        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        userService.getAllUsers(users -> {
            users.forEach(user -> {
                if (enteredUsername.equals(user.getFirstName()) && enteredPassword.equals(user.getPassword())) {



                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/Dashboard.fxml"));
                            Parent root = loader.load();

                            DashBoard dashBoardController = loader.getController();
                            dashBoardController.setLoggedInUser(user);

                            Stage dashboardStage = new Stage();
                            dashboardStage.initStyle(StageStyle.UNDECORATED);
                            dashboardStage.setScene(new Scene(root));
                            dashboardStage.show();

                            Stage currentStage = (Stage) login.getScene().getWindow();
                            currentStage.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    System.out.println("Login Failed");
                }
            });
        });


        Platform.runLater(() -> {
            username.setText("");
            password.setText("");
        });
    }


}
