package org.algoavengers.weatherwatch.services;

import org.algoavengers.weatherwatch.models.*;
import org.algoavengers.weatherwatch.services.apis.*;
import org.algoavengers.weatherwatch.storage.*;

public class WeatherWatchService {
    CacheManager cacheManager = new CacheManager(new DBManager());

    // TODO: Implement the methods below
    void saveLocation(LocationData location) {
        // Save the location data
        // cacheManager.cache.saveLocation();
    }

    void removeLocation(LocationData location) {
        // Remove the location data
        // cacheManager.cache.removeLocation();
    }

    LocationData[] getSavedLocations() {
        // Get the top 5 locations from the cache
        // return cacheManager.cache.getSavedLocations();
        return null;
    }

    // TODO: Add the 5-day forecast parameter to the method signature
    void addToCache(LocationData location, WeatherData weatherData, APData apData) {
        // Add the location, weather data and air pollution data to the cache
        cacheManager.cache.save(location, weatherData, apData);
    }

    void removeFromCache(String city) {
        // Remove the location, weather data and air pollution data from the cache
        cacheManager.cache.delete(city);
    }

    void clearExpiredData() {
        // Remove outdated records from the cache
        cacheManager.cache.deleteOutdatedRecords();
    }

    LocationData cityToCoords(String API_KEY, String city) {
        // Convert the city name to coordinates
        LocationData location = Geocoder.geocode(API_KEY, city);
        if (location == null) {
            System.out.println("An error occurred while fetching the coordinates.");
        }
        return location;
    }

    LocationData coordsToCity(String API_KEY, float lat, float lon) {
        // Convert the coordinates to a city name
        LocationData location = Geocoder.geocode(API_KEY, lat, lon);
        if (location == null) {
            System.out.println("An error occurred while fetching the city name.");
        }
        return location;
    }

    // TODO: Add the 5-day forecast to the method return
    Object[] fetchData(String API_KEY, LocationData location) {
        Object[] data = cacheManager.cache.find(location.city);
        if (data != null) {
            // If the data is already in the cache, return it
            return data;
        }
        else {
            // Fetch the data from the APIs
            WeatherData weatherData = WeatherForecaster.GetCurrentForecast(API_KEY, location);
            APData apData = APGetter.GetAPIData(API_KEY, location);
            if (weatherData == null || apData == null) {
                // If an error occurred while fetching the data, return null
                return null;
            }
            else {
                // Add the data to the cache
                addToCache(location, weatherData, apData);
                return new Object[] {weatherData, apData};
            }
        }
    }

}