package com.example.vehicle_parking.frontend.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.vehicle_parking.frontend.Model.ArrivedVehicle;
import com.example.vehicle_parking.frontend.Model.DepatureVehicle;
import com.example.vehicle_parking.frontend.Model.User;
import com.example.vehicle_parking.frontend.Model.Vehicle;
import com.example.vehicle_parking.frontend.Service.ArrivedVehicleService;
import com.example.vehicle_parking.frontend.Service.DispatchService;
import com.example.vehicle_parking.frontend.Service.VehicleService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import lombok.Setter;

public class DashBoard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML private TextField Amodel;
    @FXML private TextField AoName;
    @FXML private TextField Aphone;
    @FXML private TextField aArrT;
    @FXML private Button aPrint;
    @FXML private TextField aRef;
    @FXML private Button aSave;
    @FXML private TextField aSn;
    @FXML private Pane addUser;
    @FXML private Pane addVehicle;
    @FXML private TextField dArrivalTime;
    @FXML private TextField dAvailability;
    @FXML private TextField dDepartureTime;
    @FXML private TextField dModel;
    @FXML private TextField dON;
    @FXML private Button dPrint;
    @FXML private TextField dRef;
    @FXML private Button dSave;
    @FXML private TextField dTotalPrice;
    @FXML private ComboBox<Vehicle> dropDown;
    @FXML private Pane logout;
    @FXML private Pane report;
    @FXML private Pane setting;
    @FXML private VBox dashBoardPane;

    private final VehicleService vehicleService = new VehicleService();


    @Setter
    private User loggedInUser;

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            prepareNewArrivalForm();
            loadVehiclesToDropdown();
            startLiveClock();
            slotAlign();
        });

        addUser.setOnMouseClicked(event -> {
            addNewUser();
            applyBlur();
        });

        addVehicle.setOnMouseClicked(event -> {
            addNewVehicle();
            applyBlur();
        });



        aSave.setOnMouseClicked(event -> {
            loadVehiclesToDropdown();
            addArrVehicle(loggedInUser);
            slotAlign();
            Amodel.clear();
            AoName.clear();
            Aphone.clear();
            aRef.clear();


        });
        dRef.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                dispatchVehicle();
                dAvailability.setText(String.valueOf(true));
            }
        });

        aPrint.setOnMouseClicked(event -> {
            print(loggedInUser);
        });


    }

    public void loadVehiclesToDropdown() {
        if (dropDown != null) {
            dropDown.setStyle("-fx-font-weight: bold;");
            vehicleService.getAllVehicles(vehicles -> {
                Platform.runLater(() -> {
                    dropDown.getItems().clear();
                    dropDown.getItems().addAll(vehicles);

                    dropDown.setConverter(new StringConverter<>() {
                        @Override
                        public String toString(Vehicle vehicle) {
                            return vehicle != null ? vehicle.getName() : "";

                        }

                        @Override
                        public Vehicle fromString(String string) {
                            return dropDown.getItems().stream()
                                    .filter(vehicle -> vehicle.getName().equals(string))
                                    .findFirst()
                                    .orElse(null);

                        }
                    });

                    dropDown.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
                        @Override
                        protected void updateItem(Vehicle vehicle, boolean empty) {
                            super.updateItem(vehicle, empty);
                            setText(empty || vehicle == null ? null : vehicle.getName());
                        }
                    });

                    dropDown.setButtonCell(new javafx.scene.control.ListCell<>() {
                        @Override
                        protected void updateItem(Vehicle vehicle, boolean empty) {
                            super.updateItem(vehicle, empty);
                            setText(empty || vehicle == null ? null : vehicle.getName());
                        }
                    });
                });
            });
        } else {
            System.out.println("ComboBox 'dropDown' is not initialized yet!");
        }
    }

    public void addNewUser() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(DashBoard.class.getResource("/templates/user.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.setOnHidden(event -> removeBlur());
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addNewVehicle() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(DashBoard.class.getResource("/templates/Vehicle.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.setOnHidden(event -> {
                    removeBlur();
                    loadVehiclesToDropdown();
                });
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void applyBlur() {
        ColorAdjust dim = new ColorAdjust();
        dim.setBrightness(-0.2);
        dashBoardPane.setEffect(dim);
    }

    private void removeBlur() {
        dashBoardPane.setEffect(null);
    }

    public void addArrVehicle(User user) {
        String model = Amodel.getText();
        String ownerName = AoName.getText();
        String phone = Aphone.getText();
        Integer refNo = Integer.parseInt(aRef.getText());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime arrivalTime = LocalDateTime.parse(aArrT.getText(), formatter);

        Integer sn = Integer.parseInt(aSn.getText());

        ArrivedVehicle arrivedVehicle = new ArrivedVehicle();
        arrivedVehicle.setUser(user);

        Vehicle selectedVehicle = dropDown.getValue();
        arrivedVehicle.setVehicle(selectedVehicle);
        System.out.println(selectedVehicle);

        arrivedVehicle.setRefNo(refNo);
        arrivedVehicle.setModel(model);
        arrivedVehicle.setOwnerName(ownerName);
        arrivedVehicle.setOwnerPhone(phone);

        arrivedVehicle.setArrivalTime(arrivalTime);

        arrivedVehicle.setAvailable(false);
        arrivedVehicle.setSlotNo(sn);

        ArrivedVehicleService arrivedVehicleService = new ArrivedVehicleService();
        arrivedVehicleService.addArrivedVehicle(arrivedVehicle, success -> {
            if (success) {
                Platform.runLater(() -> {
                    loadVehiclesToDropdown();
                    prepareNewArrivalForm();
                    slotAlign();
                    Amodel.clear();
                    AoName.clear();
                    Aphone.clear();
                });
            }
            else {
                System.out.println("Error adding vehicle");
            }
        });
    }

    public void print(User user) {
        try {
            String ownerName = AoName.getText();
            Integer refNo = Integer.parseInt(aRef.getText());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime arrivalTime = LocalDateTime.parse(aArrT.getText(), formatter);

            Vehicle selectedVehicle = dropDown.getValue();
            String type = selectedVehicle.getName();
            double price = selectedVehicle.getPricePerHour();

            FXMLLoader loader = new FXMLLoader(DashBoard.class.getResource("/templates/recipt.fxml"));
            Parent root = loader.load();

            ReciptController reciptController1 = loader.getController();
            reciptController1.setDate(ownerName, refNo, arrivalTime, type, price);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            addArrVehicle(user);

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Print Error");
            alert.setHeaderText("Failed to generate receipt.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    private void prepareNewArrivalForm() {
       ArrivedVehicleService arrivedVehicleService = new ArrivedVehicleService();
       arrivedVehicleService.getLastRefNo(lastRefNo -> {
           int nextRef = lastRefNo+1;
           Platform.runLater(() -> {
               aRef.setText(Integer.toString(nextRef));
               LocalDateTime now = LocalDateTime.now();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               aArrT.setText(now.format(formatter));
           });
       });





    }

    private void startLiveClock() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    aArrT.setText(now.format(formatter));
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void slotAlign() {
        ArrivedVehicleService arrivedVehicleService = new ArrivedVehicleService();

        arrivedVehicleService.getAll(vehicles -> {
            boolean[] occupiedSlots = new boolean[101]; // Index 1-100
            int maxUsedSlot = 0;
            int availableSlot = -1;

            for (ArrivedVehicle vehicle : vehicles) {
                int slotNo = vehicle.getSlotNo();

                if (slotNo >= 1 && slotNo <= 100) {
                    if (vehicle.isAvailable()) {
                        availableSlot = slotNo;
                    } else {
                        occupiedSlots[slotNo] = true;
                    }

                    if (slotNo > maxUsedSlot) {
                        maxUsedSlot = slotNo;
                    }
                }
            }

            int nextSlot = -1;

            if (availableSlot != -1) {
                nextSlot = availableSlot;
            } else if (maxUsedSlot < 100) {
                nextSlot = maxUsedSlot + 1;
            }

            int finalNextSlot = nextSlot;

            Platform.runLater(() -> {
                if (finalNextSlot != -1) {
                    aSn.setText(String.valueOf(finalNextSlot));
                    aSave.setDisable(false);
                } else {
                    aSn.setText("Full");
                    aSave.setDisable(true);
                }
            });
        });
    }

    public void dispatchVehicle() {
        Integer ref = Integer.valueOf(dRef.getText());
        DispatchService dispatchService = new DispatchService();

        dispatchService.getByRef(vehicle -> {
            if (vehicle == null || !Objects.equals(vehicle.getRefNo(), ref)) {
                System.out.println("Vehicle not found or reference mismatch.");
                return;
            }

            Platform.runLater(() -> {
                populateDispatchForm(vehicle);

                dSave.setOnMouseClicked(mouseEvent -> {
                    DepatureVehicle depatureVehicle = buildDepartureVehicle(vehicle);
                    ArrivedVehicle updatedArrivedVehicle = buildUpdatedArrivedVehicle(vehicle);

                    Arr arr = new Arr();
                    arr.addDepVehicle(depatureVehicle);
                    arr.updateArrivedVehicle(updatedArrivedVehicle);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Success updated and saved");
                    alert.showAndWait();

                });

                dPrint.setOnMouseClicked(mouseEvent -> {
                    DPrint(vehicle.getVehicle());
                    DepatureVehicle depatureVehicle = buildDepartureVehicle(vehicle);
                    ArrivedVehicle updatedArrivedVehicle = buildUpdatedArrivedVehicle(vehicle);

                    Arr arr = new Arr();
                    arr.addDepVehicle(depatureVehicle);
                    arr.updateArrivedVehicle(updatedArrivedVehicle);


                });


            });
        }, ref);
    }


    private void populateDispatchForm(ArrivedVehicle vehicle) {
        dRef.setText(String.valueOf(vehicle.getRefNo()));
        dON.setText(vehicle.getOwnerName());
        dModel.setText(vehicle.getModel());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (vehicle.getArrivalTime() != null) {
            dArrivalTime.setText(vehicle.getArrivalTime().format(formatter));
        }

        LocalDateTime now = LocalDateTime.now();
        dDepartureTime.setText(now.format(formatter));

        double totalHours = calculateTotalHours(vehicle.getArrivalTime(), now);
        double totalPrice = totalHours * vehicle.getVehicle().getPricePerHour();

        dTotalPrice.setText(String.format("Rs. %.2f", totalPrice));
    }

    private DepatureVehicle buildDepartureVehicle(ArrivedVehicle vehicle) {
        DepatureVehicle depatureVehicle = new DepatureVehicle();

        depatureVehicle.setRefNo(vehicle.getRefNo());
        depatureVehicle.setOwnerName(vehicle.getOwnerName());
        depatureVehicle.setModel(vehicle.getModel());
        depatureVehicle.setOwnerPhone(vehicle.getOwnerPhone());
        depatureVehicle.setAvailable(false);
        depatureVehicle.setSlotNo(vehicle.getSlotNo());

        LocalDateTime arrival = vehicle.getArrivalTime();
        LocalDateTime departure = LocalDateTime.now();

        depatureVehicle.setArrivalTime(arrival);
        depatureVehicle.setDepartureTime(departure);

        double totalHours = calculateTotalHours(arrival, departure);
        depatureVehicle.setNumberOfHours(totalHours);

        double totalPrice = totalHours * vehicle.getVehicle().getPricePerHour();
        depatureVehicle.setTotalPrice(totalPrice);

        User user = new User();
        user.setId(loggedInUser.getId());
        depatureVehicle.setUser(user);

        depatureVehicle.setVehicle(vehicle.getVehicle());
        return depatureVehicle;
    }

    private ArrivedVehicle buildUpdatedArrivedVehicle(ArrivedVehicle original) {
        ArrivedVehicle updated = new ArrivedVehicle();

        updated.setId(original.getId());
        updated.setRefNo(original.getRefNo());
        updated.setOwnerName(original.getOwnerName());
        updated.setModel(original.getModel());
        updated.setOwnerPhone(original.getOwnerPhone());
        updated.setArrivalTime(original.getArrivalTime());
        updated.setAvailable(true);
        updated.setSlotNo(original.getSlotNo());
        updated.setVehicle(original.getVehicle());
        updated.setUser(loggedInUser);

        return updated;
    }

    private double calculateTotalHours(LocalDateTime arrival, LocalDateTime departure) {
        long minutes = ChronoUnit.MINUTES.between(arrival, departure);
        long hours = ChronoUnit.HOURS.between(arrival, departure);
        double remaining = minutes % 60;
        return hours + (remaining / 60.0);
    }

    public void DPrint(Vehicle vehicle){

        try {
            String ownerName = dON.getText();
            Integer refNo = Integer.parseInt(dRef.getText());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime arrivalTime = LocalDateTime.parse(dArrivalTime.getText(), formatter);

            double vehiclPrice = vehicle.getPricePerHour();
            String name = vehicle.getName();



            LocalDateTime departureTime = LocalDateTime.parse(dDepartureTime.getText(), formatter);
            double totalHours = calculateTotalHours(arrivalTime, departureTime);
            double totalPrice = totalHours * vehiclPrice;


            FXMLLoader loader = new FXMLLoader(DashBoard.class.getResource("/templates/dReciept.fxml"));
            Parent root = loader.load();

            DepaturereceiptController reciptController1 = loader.getController();
            reciptController1.setDate(refNo,ownerName,name,vehiclPrice,arrivalTime,departureTime,totalHours,totalPrice);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();


        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Print Error");
            alert.setHeaderText("Failed to generate receipt.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }


}



