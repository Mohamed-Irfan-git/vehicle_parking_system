package com.example.vehicle_parking.frontend.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.vehicle_parking.frontend.Model.Vehicle;
import com.example.vehicle_parking.frontend.Service.VehicleService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.beans.value.ChangeListener;

public class VehicleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField PriceFiled;

    @FXML
    private Button SaveButton;

    @FXML
    private TextField VehicleNameField;

    @FXML
    private ListView<Vehicle> listView;

    VehicleService vehicleService = new VehicleService();

    ChangeListener<String> vehicleNameListener;

    @FXML
    void initialize() {
        Platform.runLater(this::searchVehicle);
    }

    public void searchVehicle() {
        vehicleNameListener = (observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                vehicleService.findByName(newValue, listView);
            } else {
                listView.getItems().clear();
                listView.setVisible(false);
                listView.setManaged(false);
            }
        };

        VehicleNameField.textProperty().addListener(vehicleNameListener);

        VehicleNameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                PriceFiled.requestFocus();
            }
            if (e.getCode() == KeyCode.DOWN) {

                listView.requestFocus();
                listView.getSelectionModel().selectFirst();

            } else if (e.getCode() == KeyCode.ENTER) {
                Vehicle selectedVehicle = listView.getSelectionModel().getSelectedItem();

                if (selectedVehicle != null) {
                    // Stop triggering listener again
                    VehicleNameField.textProperty().removeListener(vehicleNameListener);
                    VehicleNameField.setText(selectedVehicle.getName());
                    PriceFiled.setText(String.valueOf(selectedVehicle.getPricePerHour()));
                    VehicleNameField.textProperty().addListener(vehicleNameListener);

                    listView.setVisible(false);
                    listView.setManaged(false);
                }
                e.consume();
            }
        });

        listView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Vehicle selectedVehicle = listView.getSelectionModel().getSelectedItem();

                if (selectedVehicle != null) {
                    // Stop triggering listener again
                    VehicleNameField.textProperty().removeListener(vehicleNameListener);
                    VehicleNameField.setText(selectedVehicle.getName());
                    PriceFiled.setText(String.valueOf(selectedVehicle.getPricePerHour()));
                    VehicleNameField.textProperty().addListener(vehicleNameListener);

                    listView.setVisible(false);
                    listView.setManaged(false);
                    PriceFiled.requestFocus();
                }
                e.consume();
            }
        });

        PriceFiled.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                SaveButton.requestFocus();

            }
        });
        SaveButton.setOnAction(event -> {
           String vehicleName = VehicleNameField.getText();
           double pricePerHour = Double.parseDouble(PriceFiled.getText());
           Vehicle vehicle = new Vehicle(vehicleName, pricePerHour);
            vehicleService.addVehicle(vehicle, () -> {
                System.out.println("Vehicle added");
                DashBoard dashBoard = new DashBoard();
                dashBoard.loadVehiclesToDropdown();

            });
            if (!vehicleName.isEmpty()&&pricePerHour>0)  {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save Vehicle");
                alert.setHeaderText(null);
                alert.setContentText("Save Vehicle");
                alert.showAndWait();
                VehicleNameField.setText("");
                PriceFiled.setText("");
            }

        });

        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Vehicle vehicle, boolean empty) {
                super.updateItem(vehicle, empty);
                if (empty || vehicle == null) {
                    setText(null);
                } else {
                    setText(vehicle.getName());
                    setStyle("-fx-font-weight: bold;");
                }
            }
        });
    }
}
