package org.algoavengers.weatherwatch.ui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class LoaderController {

    @FXML
    private ImageView logoImageView;

    public void initialize() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), logoImageView);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/algoavengers/weatherwatch/views/home-page.fxml"));
                Stage stage = (Stage) logoImageView.getScene().getWindow();
                stage.setScene(new Scene(root, 1080, 720));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pause.play();
    }
}