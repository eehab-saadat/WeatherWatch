package org.algoavengers.weatherwatch.services.apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;
import org.algoavengers.weatherwatch.utils.StringBuilderObj;

/**
 * WeatherForecaster class provides methods to fetch weather data from the OpenWeatherMap API.
 */
public class WeatherForecaster {
    private static String params = "lat=%f&lon=%f&appid=%s&units=metric&lang=en";

    /**
     * Fetches the current weather forecast for a given location.
     *
     * @param API_KEY The API key to be used for the API calls.
     * @param location The location for which the weather forecast is to be fetched.
     * @return A WeatherData object containing the current weather forecast for the location.
     */
    public static WeatherData GetCurrentForecast(String API_KEY, LocationData location) {
        String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

        // Constructing parameters
        params = String.format(params, location.getLat(), location.getLon(), API_KEY);

        // Constructing the full URL
        String apiUrl = BASE_URL + "?" + params;

        // Making the API request and getting the JSON response
        try {
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);
            // Parsing JSON response
            JsonObject json = new JsonParser().parse(response.toString()).getAsJsonObject();
            return new WeatherData(json, location);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches a 5-day weather forecast for a given location.
     *
     * @param API_KEY The API key to be used for the API calls.
     * @param location The location for which the weather forecast is to be fetched.
     * @return An array of WeatherData objects containing the 5-day weather forecast for the location.
     */
    public static WeatherData[] GetPentaDayForecast(String API_KEY, LocationData location) {
        String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

        // Constructing parameters
        params = String.format(params, location.getLat(), location.getLon(), API_KEY);

        // Constructing the full URL
        String apiUrl = BASE_URL + "?" + params;

        // Making the API request and getting the JSON response
        try {
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);
            // Parsing JSON response
            JsonObject json = new JsonParser().parse(response.toString()).getAsJsonObject();
            JsonArray list = json.getAsJsonArray("list");
            WeatherData[] weatherData = new WeatherData[5];
            int index = 0;
            // Parsing through the JSON Array to get every 8th element (1 reading per day) for the next 5 days
            for (int i = 0; i < list.size(); i++) {
                if (i % 8 == 0) {
                    weatherData[index] = new WeatherData(list.get(i).getAsJsonObject(), location);
                    index++;
                }
            }
            return weatherData;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
}