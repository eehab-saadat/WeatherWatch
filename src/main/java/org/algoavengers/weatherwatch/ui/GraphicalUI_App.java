package org.algoavengers.weatherwatch.ui;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class GraphicalUI_App extends Application implements DisplayInterface {
    private String API_KEY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        // Load the loader.fxml file
        Parent loaderRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/algoavengers/weatherwatch/views/loader.fxml")));

        // Set the loader.fxml as the scene for your primary stage
        primaryStage.setScene(new Scene(loaderRoot));
        primaryStage.show();

        // Create a PauseTransition with a duration of 3 seconds
//        PauseTransition pause = new PauseTransition(Duration.seconds(3));
//        pause.setOnFinished(event -> {
//            // Load the home-page.fxml file
//            try {
//                Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/algoavengers/weatherwatch/views/home-page.fxml")));
//
//                // Switch to the home-page.fxml screen
//                primaryStage.setScene(new Scene(homeRoot));
//            } catch (IOException e) {
//                //e.printStackTrace();
//            }
//        });
//
//        // Start the PauseTransition
//        pause.play();
    }

    @Override
    public void run(String API_KEY) {
        this.API_KEY = API_KEY;
    }
}