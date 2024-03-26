package org.algoavengers.weatherwatch.ui;

//fxml imports
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
//models imports
import org.algoavengers.weatherwatch.models.*;


public class HomePageController {

    private WeatherData currentWeather;
    private LocationData location;
    private WeatherData[] forecast;
    private APData APdata;
    @FXML
    private Label temp_label;
    @FXML
    private Label main_label, description_label;

    @FXML
    private Rectangle rectangleLahore;
    @FXML
    private Rectangle rectangleIstanbul;
    @FXML
    private Rectangle rectangleLondon;

    @FXML
    private Label labelLahore;
    @FXML
    private Label labelIstanbul;
    @FXML
    private Label labelLondon;

    @FXML
    public void handleQuickAccess() {
        rectangleLahore.setVisible(true);
        rectangleIstanbul.setVisible(true);
        rectangleLondon.setVisible(true);
        labelLahore.setVisible(true);
        labelIstanbul.setVisible(true);
        labelLondon.setVisible(true);
        main_label.setText("hello");
    }
    public void updateLabels(String lahoreData, String istanbulData, String londonData) {
        labelLahore.setText(lahoreData);
        labelIstanbul.setText(istanbulData);
        labelLondon.setText(londonData);
        temp_label.setText("hello");
    }

}