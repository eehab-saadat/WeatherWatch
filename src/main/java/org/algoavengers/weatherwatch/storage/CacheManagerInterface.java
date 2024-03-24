package org.algoavengers.weatherwatch.storage;

import org.algoavengers.weatherwatch.models.*;

public interface CacheManagerInterface {
    void save(LocationData location, WeatherData weatherData, APData apData);
    void delete(String city);
    Object[] find(String city);
    LocationData[] top5Locations();
    void deleteOutdatedRecords();
}