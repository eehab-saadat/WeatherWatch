package org.algoavengers.weatherwatch.ui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class LoaderController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private AnchorPane rootPane;

    private int colorIndex = 0;
    private final String[] colors = {"#F8E9CD","#BDB2FF", "#14213d"};

    public void initialize() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), logoImageView); // Increase the duration to 2 seconds
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

        // Create a new Timeline
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.9), event -> { // Change the color every 1 seconds
                    // Cycle through the colors
                    if(this.rootPane != null) {
                        rootPane.setStyle("-fx-background-color: " + colors[colorIndex]);
                        colorIndex = (colorIndex + 1) % colors.length;
                    }
                })
        );

        PauseTransition pause = new PauseTransition(Duration.seconds(5)); // Change the total duration to 5 seconds
        pause.setOnFinished(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/org/algoavengers/weatherwatch/views/home-page.fxml"));
                if(logoImageView.getScene() != null && logoImageView != null) {
                    Stage stage = (Stage) logoImageView.getScene().getWindow();
                    stage.setScene(new Scene(root, 1080, 680));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Set the cycle count for the timeline
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        timeline.play();
        pause.play();
    }
}
