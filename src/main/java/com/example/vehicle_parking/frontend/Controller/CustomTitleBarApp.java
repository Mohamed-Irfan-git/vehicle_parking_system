package com.example.vehicle_parking.frontend.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CustomTitleBarApp {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private AnchorPane titleBar;


    @FXML
    private Label titleLabel;

    @FXML
    void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) (minimizeButton.getScene().getWindow());
        stage.setIconified(true);

    }
    @FXML
    private void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);

    }

    @FXML
    void closewindow(ActionEvent event) {
        Stage stage = (Stage) (closeButton.getScene().getWindow());
        stage.close();

    }

    @FXML
    void initialize() {
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'titlebar.fxml'.";
        assert minimizeButton != null : "fx:id=\"minimizeButton\" was not injected: check your FXML file 'titlebar.fxml'.";
        assert titleBar != null : "fx:id=\"titleBar\" was not injected: check your FXML file 'titlebar.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'titlebar.fxml'.";

    }

}