package org.algoavengers.weatherwatch.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomLoadingScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene loadingScene = createLoadingScene();
        primaryStage.setScene(loadingScene);
        primaryStage.show();
    }

    public Scene createLoadingScene() {
        // Create a spinning wheel to show the loading progress
        ProgressIndicator progressIndicator = new ProgressIndicator();

        // Create a label for the loading message
        Label loadingLabel = new Label("Loading...");

        // Create a vertical box to hold the spinning wheel and the loading message
        VBox loadingPane = new VBox();
        loadingPane.setAlignment(Pos.CENTER); // Now Pos is recognized
        loadingPane.getChildren().addAll(progressIndicator, loadingLabel);

        // Set the style of the spinning wheel and the loading message
        loadingPane.setStyle("-fx-font-size: 32pt; -fx-text-fill: white; -fx-background-color: black;");
        progressIndicator.setStyle("-fx-progress-color: white;");

        // Create a stack pane to center the vertical box
        StackPane root = new StackPane();
        root.getChildren().add(loadingPane);

        // Create a scene for the loading screen
        Scene loadingScene = new Scene(root, 400, 235);

        return loadingScene;
    }
}