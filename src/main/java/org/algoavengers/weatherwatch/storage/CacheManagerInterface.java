package org.algoavengers.weatherwatch.storage;

import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;

public interface CacheManagerInterface {

    void save(LocationData location, WeatherData weatherData, APData apData);
    void delete(String city);
    Object[] find(String city);

    LocationData[] top5Locations();
    void deleteOutdatedRecords();
}