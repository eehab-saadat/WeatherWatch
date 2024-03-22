package org.algoavengers.weatherwatch.Services.apis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

public class Geocoder {
    public static float[] geocode(String API_KEY, String city) {
        // Constructing the API URL
        String apiUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + API_KEY;

        try {
            // Making the API request and getting the JSON response
            StringBuilder response = StringBuilderObj.getStringBuilder(apiUrl);

            // Parsing JSON response
            // Parse String to JsonArray
            JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();

            // Get the first JsonObject from the JsonArray
            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

            // Get latitude and longitude
            float latitude = jsonObject.get("lat").getAsFloat();
            float longitude = jsonObject.get("lon").getAsFloat();

            // Creating the float array to store latitude and longitude
            return new float[]{latitude, longitude};

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
}
