package org.algoavengers.weatherwatch.ui;

//fxml imports
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.algoavengers.weatherwatch.utils.CountryCodeConverter;
import org.controlsfx.control.WorldMapView;
import org.algoavengers.weatherwatch.ui.HomePageController;
import java.io.IOException;

/**
 * This class is the controller for the SearchMap view.
 * It handles the interaction between the user and the WorldMapView.
 */
public class SearchMapController {

    @FXML
    private WorldMapView worldMap;

    /**
     * This method is called after all @FXML annotated members have been injected.
     * It sets up a listener for the selected countries in the WorldMapView.
     * When a country is selected, it gets the capital city of the country and loads the home page with the weather data for that city.
     */
    public void initialize() {
        worldMap.getSelectedCountries().addListener((ListChangeListener<WorldMapView.Country>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (WorldMapView.Country country : change.getAddedSubList()) {
                        // getting the city name form country code
                        System.out.println("Selected country: " + country.name());
                        String capital = CountryCodeConverter.getCapitalCity(country.name());
                        System.out.println("Capital city: " + capital);
                        // loading the home page
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/algoavengers/weatherwatch/views/home-page.fxml"));
                            Parent mapRoot = loader.load();
                            HomePageController homeController = loader.getController();
                            homeController.searchByCity(capital);
                            Stage stage = (Stage) worldMap.getScene().getWindow();
                            Scene scene = new Scene(mapRoot, 1080, 680);
                            stage.setScene(scene);
                            stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/algoavengers/weatherwatch/assets/logo.png")));
                            stage.setTitle("WeatherWatch (Home page)");
                            stage.setIconified(false);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

}