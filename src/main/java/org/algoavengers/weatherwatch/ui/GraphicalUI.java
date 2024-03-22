package org.algoavengers.weatherwatch.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GraphicalUI extends Application implements DisplayInterface {
    private String API_KEY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/algoavengers/weatherwatch/views/hello-view.fxml")));
        primaryStage.setTitle("Hello View");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void run(String API_KEY) {
        this.API_KEY = API_KEY;
        System.out.println("Displaying Graphical User Interface");
        launch();
    }
}