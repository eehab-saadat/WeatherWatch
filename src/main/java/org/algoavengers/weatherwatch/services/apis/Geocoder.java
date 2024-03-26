package org.algoavengers.weatherwatch.services.apis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.utils.StringBuilderObj;

/**
 * Geocoder class provides a method to fetch geographical location data for a specific city.
 * It uses the OpenWeatherMap Geocoding API to fetch the data.
 */
public class Geocoder {
    private static String BASE_URL = "https://api.openweathermap.org/geo/1.0/";
    private static String params = "";
    private static String apiUrl = "";
    /**
     * Fetches the geographical location data for a specific city.
     *
     * @param API_KEY The API key for the OpenWeatherMap API.
     * @param city The city for which the location data is to be fetched.
     * @return A LocationData object containing the location data for the city.
     */
    public static LocationData geocode(String API_KEY, String city) {
        // Constructing the API URL
        params = "direct?q=" + city + "&limit=1&appid=" + API_KEY;
        apiUrl = BASE_URL + params;

        try {
            // Making the API request and getting the JSON response
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);

            // Parsing JSON response
            // Parse String to JsonArray
            JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();

            // Get the first JsonObject from the JsonArray
            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            System.out.println(jsonObject.toString());

            // Returning Location Data
            return new LocationData(
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("country").getAsString(),
                    jsonObject.get("lat").getAsFloat(),
                    jsonObject.get("lon").getAsFloat()
            );

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
    public static LocationData geocode(String API_KEY, float lat, float lon) {
        // Constructing the API URL
        params = "reverse?lat=" + lat + "&lon=" + lon + "&limit=1&appid=" + API_KEY;
        apiUrl = BASE_URL + params;

        try {
            // Making the API request and getting the JSON response
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);

            // Parsing JSON response
            // Parse String to JsonArray
            JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();

            // Get the first JsonObject from the JsonArray
            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            System.out.println(jsonObject.toString());

            // Returning Location Data
            return new LocationData(
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("country").getAsString(),
                    jsonObject.get("lat").getAsFloat(),
                    jsonObject.get("lon").getAsFloat()
            );

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
}