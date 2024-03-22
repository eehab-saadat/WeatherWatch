package org.algoavengers.weatherwatch.apis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.algoavengers.weatherwatch.utils.StringBuilderObj;

public class WeatherForecaster {
    private static String params = "lat=%f&lon=%f&appid=%s&units=metric&lang=en";
    public static JsonObject CurrentForecast(String API_KEY, float lat, float lon) {
        String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

        // Constructing parameters
        params = String.format(params, lat, lon, API_KEY);

        // Constructing the full URL
        String apiUrl = BASE_URL + "?" + params;

        // Making the API request and getting the JSON response
        try {
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);
            // Parsing JSON response
            return new JsonParser().parse(response.toString()).getAsJsonObject();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    public JsonObject PentaDayForecast(String API_KEY, float lat, float lon) {
        String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

        // Constructing parameters
        params = String.format(params, lat, lon, API_KEY);

        // Constructing the full URL
        String apiUrl = BASE_URL + "?" + params;

        // Making the API request and getting the JSON response
        try {
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);
            // Parsing JSON response
            return new JsonParser().parse(response.toString()).getAsJsonObject();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
}
