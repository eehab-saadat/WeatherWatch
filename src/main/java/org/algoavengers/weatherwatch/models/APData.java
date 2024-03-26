package org.algoavengers.weatherwatch.models;

import com.google.gson.JsonObject;
import org.algoavengers.weatherwatch.utils.DTConverter;

/**
 * APData class represents the air pollution data for a specific location.
 * It includes details like air quality index, various gas concentrations, and a comment on the air quality.
 */
public class APData {
    public LocationData location;
    public int aqi;
    public float co, no, no2, o3, so2, pm2_5, pm10, nh3;
    public String comment, dt;

    /**
     * Default constructor for APData class.
     * It initializes the air pollution data with default values.
     */
    public APData() {
        location = null;
        aqi = 0;
        co = no = no2 = o3 = so2 = pm2_5 = pm10 = nh3 = 0;
        comment = dt = "";
    }

    /**
     * Constructor for APData class.
     * It initializes the air pollution data using the provided JSON object and location data.
     *
     * @param json The JSON object containing the air pollution data.
     * @param location The location data for which the air pollution data is fetched.
     */
    public APData(JsonObject json, LocationData location) {
        this.location = location;

        JsonObject listObject = json.getAsJsonArray("list").get(0).getAsJsonObject();
        this.aqi = listObject.getAsJsonObject("main").get("aqi").getAsInt();

        JsonObject components = listObject.getAsJsonObject("components");
        this.co = components.get("co").getAsFloat();
        this.no = components.get("no").getAsFloat();
        this.no2 = components.get("no2").getAsFloat();
        this.o3 = components.get("o3").getAsFloat();
        this.so2 = components.get("so2").getAsFloat();
        this.pm2_5 = components.get("pm2_5").getAsFloat();
        this.pm10 = components.get("pm10").getAsFloat();
        this.nh3 = components.get("nh3").getAsFloat();

        // Assigning a comment based on the air quality index
        switch (aqi) {
            case 1:
                this.comment = "Good";
                break;
            case 2:
                this.comment = "Fair";
                break;
            case 3:
                this.comment = "Moderate";
                break;
            case 4:
                this.comment = "Poor";
                break;
            case 5:
                this.comment = "Very Poor";
                break;
        }

        long timestamp = listObject.get("dt").getAsLong();
        this.dt = DTConverter.convertToDateTime(timestamp);
    }

    /**
     * Method to display the air pollution details in the console.
     * It prints the location, air quality index, gas concentrations, comment on the air quality, and the fetch time.
     */
    public void displayDetails() {
        System.out.println("City: " + location.city + ", " + location.country);
        System.out.println("Latitude: " + location.lat);
        System.out.println("Longitude: " + location.lon);
        System.out.println("Air Quality Index: " + aqi);
        System.out.println("Carbon Monoxide: " + co);
        System.out.println("Nitrogen Monoxide: " + no);
        System.out.println("Nitrogen Dioxide: " + no2);
        System.out.println("Ozone: " + o3);
        System.out.println("Sulphur Dioxide: " + so2);
        System.out.println("PM 2.5: " + pm2_5);
        System.out.println("PM 10: " + pm10);
        System.out.println("Ammonia: " + nh3);
        System.out.println("Comment: " + comment);
        System.out.println("Time: " + dt);
    }
}