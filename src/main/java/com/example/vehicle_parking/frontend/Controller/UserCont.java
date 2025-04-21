package com.example.vehicle_parking.frontend.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.vehicle_parking.frontend.Model.User;
import com.example.vehicle_parking.frontend.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class UserCont {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emaiField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ListView<User> listView;

    @FXML
    private TextField nicField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private Button saveBtn;

    @FXML
    void initialize() {
        keyListener();

        saveBtn.setOnAction(event -> {
            addUser();
            firstNameField.clear();
            passwordField.clear();
            nicField.clear();
            lastNameField.clear();
            addressField.clear();
            phoneField.clear();
            addressField.clear();
        });


    }
    public void keyListener(){
        firstNameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                lastNameField.requestFocus();
            }
        });
        lastNameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                emaiField.requestFocus();
            }
        });
        emaiField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                phoneField.requestFocus();
            }
        });
        phoneField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                nicField.requestFocus();
            }

        });
        nicField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addressField.requestFocus();
            }
        });
        addressField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addUser();
            }
        });
    }

    public void addUser(){
        String name = firstNameField.getText();
        String password = passwordField.getText();
        String nic = nicField.getText();
        String lastName = lastNameField.getText();
        String email = emaiField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if(!name.isEmpty() && !password.isEmpty() && !nic.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
            User user = new User(name, lastName, email, password, phone, address, nic);
            UserService userService = new UserService();
            userService.addUser(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User successfully added");
            alert.showAndWait();


        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }






    }

}
