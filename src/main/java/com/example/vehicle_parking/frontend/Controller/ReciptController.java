package com.example.vehicle_parking.frontend.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ReciptController {

    @FXML
    private Label OwnerName;

    @FXML
    private Label PricePerHour;

    @FXML
    private Label RefNo;

    @FXML
    private Label VehicleType;

    @FXML
    private Label dateLabale;

    @FXML
    private VBox footerBox;

    @FXML
    private Label headerBox;

    @FXML
    private GridPane itemsBox;

    @FXML
    private VBox receiptVBox;

    @FXML
    void initialize() {




    }
    public void setDate(String ownerName,  Integer refNo,    LocalDateTime arrivalTime, String type,  double price) {

        RefNo.setText(refNo.toString());
        OwnerName.setText(ownerName);
        VehicleType.setText(type);
        PricePerHour.setText(price+"/=");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateLabale.setText(arrivalTime.format(formatter));

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
