package org.algoavengers.weatherwatch.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class PopupController {

    @FXML
    private Label messageLabel;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }

    @FXML
    public void initialize() {
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> stage.close());
        delay.play();
        System.out.println("PopupController initialize method called");
    }
}