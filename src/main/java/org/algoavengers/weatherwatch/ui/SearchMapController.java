package org.algoavengers.weatherwatch.ui;

//fxml imports
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
//models imports
import org.algoavengers.weatherwatch.models.*;
import org.controlsfx.control.WorldMapView;
import org.controlsfx.control.WorldMapView;

public class SearchMapController {

    @FXML
    private WorldMapView worldMap;
    public void initialize() {
        worldMap.getSelectedCountries().addListener((ListChangeListener<WorldMapView.Country>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (WorldMapView.Country country : change.getAddedSubList()) {
                        System.out.println("Selected country: " + country.name());

                    }
                }
            }
        });
    }

}
