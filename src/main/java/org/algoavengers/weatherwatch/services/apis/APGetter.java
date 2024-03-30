package org.algoavengers.weatherwatch.services.apis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.utils.StringBuilderObj;

/**
 * APGetter class provides a method to fetch air pollution data from the OpenWeatherMap API.
 */
public class APGetter {
    private static String params = "lat=%f&lon=%f&appid=%s";

    /**
     * Fetches the air pollution data for a given location.
     *
     * @param API_KEY The API key to be used for the API calls.
     * @param location The location for which the air pollution data is to be fetched.
     * @return An APData object containing the air pollution data for the location.
     */
    public static APData GetAPIData(String API_KEY, LocationData location) {
        String BASE_URL = "http://api.openweathermap.org/data/2.5/air_pollution";

        // Constructing parameters
        params = String.format(params, location.getLat(), location.getLon(), API_KEY);

        // Constructing the full URL
        String apiUrl = BASE_URL + "?" + params;
        // String apiUrl = BASE_URL + "?q=" + location.city + "&appid=" + API_KEY;

        // Making the API request and getting the JSON response
        try {
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);
            // Parsing JSON response
            JsonObject json = new JsonParser().parse(response.toString()).getAsJsonObject();
            return new APData(json, location);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
}