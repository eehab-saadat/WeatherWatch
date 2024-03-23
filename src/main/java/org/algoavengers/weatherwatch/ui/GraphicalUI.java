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

public class GraphicalUI extends Application implements DisplayInterface {
    private String API_KEY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        // Create an instance of CustomLoadingScreenExample
        CustomLoadingScreen loadingScreen = new CustomLoadingScreen();

        // Show the loading screen
        Scene loadingScene = loadingScreen.createLoadingScene();
        primaryStage.setScene(loadingScene);
        primaryStage.show();

        // Create a PauseTransition with a duration of 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            // Load the hello-view.fxml file
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/algoavengers/weatherwatch/views/hello-view.fxml")));

                // Switch to the hello-view.fxml screen
                primaryStage.setTitle("Hello View");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start the PauseTransition
        pause.play();
    }

    @Override
    public void run(String API_KEY) {
        this.API_KEY = API_KEY;
    }
}