package org.algoavengers.weatherwatch.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;

public class PopupController {

//    @FXML
//    private Label messageLabel;
//
//    private Stage stage;
//
//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }
//
//    public void displayMessage(String message) {
//        messageLabel.setText(message);
//    }
//
//    @FXML
//    public void initialize() {
//        PauseTransition delay = new PauseTransition(Duration.seconds(5));
//        delay.setOnFinished(event -> stage.close());
//        delay.play();
//        System.out.println("PopupController initialize method called");
//    }
    void showPopup(String message) {
        // create and show an alert message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.show();
    }
}