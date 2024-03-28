package org.algoavengers.weatherwatch.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.algoavengers.weatherwatch.models.*;
import org.algoavengers.weatherwatch.storage.*;
import org.algoavengers.weatherwatch.services.apis.*;
import org.algoavengers.weatherwatch.ui.LoaderController;

import java.io.FileReader;
import java.io.IOException;

/**
 * This class provides services for managing weather data and location data.
 * It interacts with the cache to store and retrieve data.
 */
public class WeatherWatchService {
    CacheManager cacheManager = new CacheManager(new DBManager());
    String API_KEY;

    public WeatherWatchService() {
        try {// get the API key
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("src/main/resources/API_KEY.json"));
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            API_KEY = jsonObject.get("API_KEY").getAsString();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Saves the given location data to the cache.
     *
     * @param location The location data to be saved.
     */
    // TODO: Implement this method (awaiting db/file implementation)
    public void saveLocation(LocationData location) {
        // Save the location data
        cacheManager.cache.saveLocation(location);
    }

    /**
     * Removes the given location data from the cache.
     *
     * @param location The location data to be removed.
     */
    // TODO: Implement this method (awaiting db/file implementation)
    public void removeLocation(LocationData location) {
        // Remove the location data
        cacheManager.cache.removeLocation(location.city);
    }

   public void setHeatWaveTrigger(LocationData location) {
        // Set the heat wave trigger for the given location
        try {
            Triggers.setTrigger(API_KEY, "temp", 321, location.getLat(), location.getLon());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void setSnowTrigger(LocationData location) {
        // Set the snow trigger for the given location
        try {
            Triggers.setTrigger(API_KEY, "temp", 265, location.getLat(), location.getLon());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void setHurricaneTrigger(LocationData location) {
        // Set the hurricane trigger for the given location
        try {
            Triggers.setTrigger(API_KEY, "wind_speed", 40, location.getLat(), location.getLon());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public String getTrigger(String id) {
        try {
            return Triggers.getTrigger(API_KEY, id);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves the top 5 saved locations from the cache.
     *
     * @return An array of LocationData objects representing the top 5 saved locations.
     */
    // TODO: Implement this method (awaiting db/file implementation)
    public LocationData[] getSavedLocations() {
        // Get the top 5 locations from the cache
        try {
            return cacheManager.cache.getSavedLocations();
        }
        catch (Exception e) {
            System.out.println("An error occurred: no saved locations found.");
        }
        return null;
    }

    /**
     * Adds the given location, weather data and air pollution data to the cache.
     *
     * @param location    The location data to be added.
     * @param weatherData The weather data to be added.
     * @param apData      The air pollution data to be added.
     */
    // TODO: Add 5-day forecast data to the parameters
    public void addToCache(LocationData location, WeatherData weatherData, APData apData, WeatherData[] forecastData) {
        // Add the location, weather data and air pollution data to the cache
        cacheManager.cache.save(location, weatherData, apData, forecastData);
    }

    /**
     * Removes the data associated with the given city from the cache.
     *
     * @param city The name of the city whose data is to be removed.
     */
    public void removeFromCache(String city) {
        // Remove the location, weather data and air pollution data from the cache
        cacheManager.cache.delete(city);
    }

    /**
     * Removes outdated records from the cache.
     */
    public void clearExpiredData() {
        // Remove outdated records from the cache
        cacheManager.cache.deleteOutdatedRecords();
    }

    /**
     * Converts the given city name to coordinates using the Geocoder API.
     *
     * @param city    The name of the city to be converted to coordinates.
     * @return A LocationData object representing the coordinates of the city.
     */
    public LocationData cityToCoords(String city) {
        // Convert the city name to coordinates
        LocationData location = Geocoder.geocode(API_KEY, city);
        if (location == null) {
            System.out.println("An error occurred while fetching the coordinates.");
        }
        return location;
    }

    /**
     * Converts the given coordinates to a city name using the Geocoder API.
     *
     * @param lat     The latitude to be converted to a city name.
     * @param lon     The longitude to be converted to a city name.
     * @return A LocationData object representing the city name of the coordinates.
     */
    public LocationData coordsToCity(float lat, float lon) {
        // Convert the coordinates to a city name
        LocationData location = Geocoder.geocode(API_KEY, lat, lon);
        if (location == null) {
            System.out.println("An error occurred while fetching the city name.");
        }
        return location;
    }

    /**
     * Fetches weather data and air pollution data for the given location.
     * If the data is already in the cache, it is returned from there.
     * Otherwise, the data is fetched from the APIs and then added to the cache.
     *
     * @param location The location for which the data is to be fetched.
     * @return An array containing the weather data and air pollution data.
     */
    // TODO: Add 5-day forecast data to the return value
    public Object[] fetchData(LocationData location) {
        Object[] data = cacheManager.cache.find(location.city);
        if (data != null) {
            // If the data is already in the cache, return it
            return data;
        } else {
            // Fetch the data from the APIs
            WeatherData weatherData = WeatherForecaster.GetCurrentForecast(API_KEY, location);
            APData apData = APGetter.GetAPIData(API_KEY, location);
            WeatherData[] forecastData = WeatherForecaster.GetPentaDayForecast(API_KEY, location);
            if (weatherData == null || apData == null) {
                // If an error occurred while fetching the data, return null
                return null;
            } else {
                // Add the data to the cache
                addToCache(location, weatherData, apData, forecastData);
                return new Object[]{location, weatherData, apData, forecastData};
            }
        }
    }

}