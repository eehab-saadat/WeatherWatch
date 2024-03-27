package org.algoavengers.weatherwatch.storage;

import org.algoavengers.weatherwatch.models.*;

import java.sql.SQLException;

public interface CacheManagerInterface {
    public void save(LocationData location, WeatherData weatherData, APData apData, WeatherData[] weatherForecasts);
    public void save(LocationData location, WeatherData weatherData, APData apData);
    void delete(String city);
    public Object[] find(String city);
    public LocationData[] getTop5Locations();
    public void deleteOutdatedRecords();
    public LocationData[] getSavedLocations();
    public void saveLocation(LocationData location);
    public void removeLocation(String city);
}
