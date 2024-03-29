//package org.algoavengers.weatherwatch.ui;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.Objects;
//
//public class PopupMain extends Application {
//
//    private final String message;
//
//    public PopupMain(String message) {
//        this.message = message;
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/algoavengers/weatherwatch/views/popup.fxml")));
//            Parent root = loader.load();
//            PopupController controller = loader.getController();
//            controller.setStage(primaryStage);
//            controller.displayMessage(message);
//            primaryStage.setScene(new Scene(root));
//            primaryStage.show();
//            System.out.println("PopupMain start method called");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}