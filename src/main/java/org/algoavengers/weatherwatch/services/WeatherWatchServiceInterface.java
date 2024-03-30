package org.algoavengers.weatherwatch.services;

//imports
import com.google.gson.Gson;
import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;

import java.util.HashMap;
public interface WeatherWatchServiceInterface {

    void saveLocation(LocationData location);
    void removeLocation(LocationData location);
    void setHeatWaveTrigger(LocationData location);
    void setSnowTrigger(LocationData location);
    void setHurricaneTrigger(LocationData location);
    String getTrigger(String id);
    LocationData[] getSavedLocations();
    void addToCache(LocationData location, WeatherData weatherData, APData apData, WeatherData[] forecastData);
    void removeFromCache(String city);
    void clearExpiredData();
    LocationData cityToCoords(String city);
    LocationData coordsToCity(float lat, float lon);
    Object[] fetchData(LocationData location);
}
