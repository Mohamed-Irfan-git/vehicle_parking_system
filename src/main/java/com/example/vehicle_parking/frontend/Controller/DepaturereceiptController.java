package com.example.vehicle_parking.frontend.Controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DepaturereceiptController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label OwnerName;

    @FXML
    private Label PricePerHour;

    @FXML
    private Label RefNo;

    @FXML
    private Label VehicleType;

    @FXML
    private Label arrivelTime;

    @FXML
    private Label dateLabale;

    @FXML
    private Label depatureTime;

    @FXML
    private VBox footerBox;

    @FXML
    private Label headerBox;

    @FXML
    private GridPane itemsBox;

    @FXML
    private VBox receiptVBox;

    @FXML
    private Label totalPrice;

    @FXML
    private Label totalhours;

    @FXML
    void initialize() {


    }
    public void setDate(int refNo, String ownerName, String vehicleType, double pricePerHour,
                        LocalDateTime arrivalTime, LocalDateTime departureTime,
                        double totalHours, double totalRate){

        RefNo.setText(String.valueOf(refNo));
        OwnerName.setText(ownerName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PricePerHour.setText(String.format("Rs. %.2f", pricePerHour));
        arrivelTime.setText(arrivalTime.format(formatter));
        depatureTime.setText(departureTime.format(formatter));
        VehicleType.setText(vehicleType);
        totalhours.setText(String.format("%.2f",totalHours));
        totalPrice.setText(String.format("Rs. %.2f",totalRate));
        dateLabale.setText(LocalDateTime.now().format(formatter));


        printNode(receiptVBox);

    }

    private void printNode(Node node) {
        Platform.runLater(() -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(receiptVBox.getScene().getWindow())) {
                boolean success = job.printPage(node);
                if (success) {
                    job.endJob();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Printing failed!");
                    alert.show();
                }
            }
        });
    }

}
