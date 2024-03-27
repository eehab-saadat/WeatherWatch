package org.algoavengers.weatherwatch.ui;

//fxml imports
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.Accordion;
//models imports
import org.algoavengers.weatherwatch.models.*;


public class HomePageController {
    // assets
    private String assetsPath = "src/main/resources/org/algoavengers/weatherwatch/assets/";
    // Weather Data from backend
    private WeatherData currentWeather;
    private LocationData currentLocation;
    private LocationData[] savedLocations;
    private WeatherData[] forecast;
    private APData currentAPData;
    // Accordion
    @FXML
    private Accordion accordion;

    // Buttons
    @FXML
    private Button saved_locations_button;
    @FXML
    private Button saveButton;
    // images
    @FXML
    private ImageView mainWeatherIcon;
    @FXML
    private ImageView FmainIcon1;
    @FXML
    private ImageView FmainIcon2;
    @FXML
    private ImageView FmainIcon3;
    @FXML
    private ImageView FmainIcon4;
    @FXML
    private ImageView FmainIcon5;
    // Labels
    @FXML
    private Label locationName;
    @FXML
    private Label tempLabel;
    @FXML
    private Label todayDate;
    @FXML
    private Label feelsLikeTemp;
    @FXML
    private Label windLabel;
    @FXML
    private Label maxTempLabel;
    @FXML
    private Label sunriseTime;
    @FXML
    private Label visibilityLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label minTempLabel;
    @FXML
    private Label sunsetTime;
    @FXML
    private Label pressureLabel;
    @FXML
    private Label mainWeather;
    @FXML
    private Label description;
    // Air Quality Labels
    @FXML
    private Label aqiValue;
    @FXML
    private Label COLevel;
    @FXML
    private Label NOLevel;
    @FXML
    private Label NO2Level;
    @FXML
    private Label sulphurLevel;
    @FXML
    private Label pm2Level;
    @FXML
    private Label pm10Level;
    // Forecast Labels
    @FXML
    private Label Fdate1;
    @FXML
    private Label Fdate2;
    @FXML
    private Label Fdate3;
    @FXML
    private Label Fdate4;
    @FXML
    private Label Fdate5;
    @FXML
    private Label Ftemp1;
    @FXML
    private Label Ftemp2;
    @FXML
    private Label Ftemp3;
    @FXML
    private Label Ftemp4;
    @FXML
    private Label Ftemp5;
    @FXML
    private Label Fmain1;
    @FXML
    private Label Fmain2;
    @FXML
    private Label Fmain3;
    @FXML
    private Label Fmain4;
    @FXML
    private Label Fmain5;
    @FXML
    private Label Fdescription1;
    @FXML
    private Label Fdescription2;
    @FXML
    private Label Fdescription3;
    @FXML
    private Label Fdescription4;
    @FXML
    private Label Fdescription5;
    @FXML
    private Label FmaxTemp1;
    @FXML
    private Label FmaxTemp2;
    @FXML
    private Label FmaxTemp3;
    @FXML
    private Label FmaxTemp4;
    @FXML
    private Label FmaxTemp5;
    @FXML
    private Label FminTemp1;
    @FXML
    private Label FminTemp2;
    @FXML
    private Label FminTemp3;
    @FXML
    private Label FminTemp4;
    @FXML
    private Label FminTemp5;
    @FXML
    private Label Fhumidity1;
    @FXML
    private Label Fhumidity2;
    @FXML
    private Label Fhumidity3;
    @FXML
    private Label Fhumidity4;
    @FXML
    private Label Fhumidity5;


    // Rectangles
    @FXML
    private Rectangle aqiStatus;

    @FXML
    public void handleQuickAccess() {

    }

    public void displayData() {
        // main card info
        if(currentLocation.city.length()>0)
            locationName.setText(currentLocation.city + ", " + currentLocation.country);
        else locationName.setText(currentLocation.lat + ", " + currentLocation.lon);
        tempLabel.setText(currentWeather.temp + "°C");
        todayDate.setText(currentWeather.dt);
        feelsLikeTemp.setText(currentWeather.feelsLike + "°C");
        mainWeather.setText(currentWeather.main);
        description.setText(currentWeather.description);
        windLabel.setText(currentWeather.windSpeed + "m/s");
        maxTempLabel.setText(currentWeather.tempMax + "°C");
        sunriseTime.setText(currentWeather.sunrise);
        visibilityLabel.setText(currentWeather.visibility + "m");
        humidityLabel.setText(currentWeather.humidity + "%");
        minTempLabel.setText(currentWeather.tempMin + "°C");
        sunsetTime.setText(currentWeather.sunset);
        pressureLabel.setText(currentWeather.pressure + "hPa");
        mainWeatherIcon.setImage(new Image("file:" + assetsPath + currentWeather.icon + ".png"));

        // air quality info
        aqiValue.setText(currentAPData.aqi + "");
        COLevel.setText(currentAPData.co + " µg/m³");
        NOLevel.setText(currentAPData.no + " µg/m³");
        NO2Level.setText(currentAPData.no2 + " µg/m³");
        sulphurLevel.setText(currentAPData.so2 + " µg/m³");
        pm2Level.setText(currentAPData.pm2_5 + " µg/m³");
        pm10Level.setText(currentAPData.pm10 + " µg/m³");
        if (currentAPData.aqi > 0 && currentAPData.aqi <= 50)
            aqiStatus.setFill(Color.GREEN);
        else if (currentAPData.aqi > 50 && currentAPData.aqi <= 100)
            aqiStatus.setFill(Color.YELLOW);
        else if (currentAPData.aqi > 100 && currentAPData.aqi <= 150)
            aqiStatus.setFill(Color.ORANGE);
        else if (currentAPData.aqi > 150 && currentAPData.aqi <= 200)
            aqiStatus.setFill(Color.ORANGERED);
        else if (currentAPData.aqi > 200 && currentAPData.aqi <= 300)
            aqiStatus.setFill(Color.RED);
        else
            aqiStatus.setFill(Color.BLACK);

        // forecast

    }

    //setters
    public void setCurrentWeather(WeatherData currentWeather) {
        this.currentWeather = currentWeather;
    }
    public  void setCurrentLocation(LocationData currentLocation) {
        this.currentLocation = currentLocation;
    }
    public void setCurrentAPData(APData currentAPData) {
        this.currentAPData = currentAPData;
    }
    public void setForecast(WeatherData[] forecast) {
        this.forecast = forecast;
    }
    public void setAllData(LocationData loc, WeatherData weather, APData apData, WeatherData[] forecast) {
        setCurrentLocation(loc);
        setCurrentWeather(weather);
        setCurrentAPData(apData);
        setForecast(forecast);
    }

    // others
    public void loadForecastArrays() {
        Label[] Fdates = new Label[5];
        Label[] Ftemps = new Label[5];
        Label[] Fmains = new Label[5];
        ImageView[] FmainIcons = new ImageView[5];
        Label[] Fdescriptions = new Label[5];
        Label[] FmaxTemps = new Label[5];
        Label[] FminTemps = new Label[5];
        Label[] Fhumidities = new Label[5];
    }
}