package org.algoavengers.weatherwatch.models;

import com.google.gson.JsonObject;
import org.algoavengers.weatherwatch.utils.DTConverter;

/**
 * WeatherData class represents the weather data for a specific location.
 * It includes details like temperature, humidity, wind speed, visibility, and weather description.
 */
public class WeatherData {
    LocationData location;
    public float temp, feelsLike, tempMin, tempMax, pressure, humidity, windSpeed, visibility;
    public String main, description, icon;
    public String sunrise, sunset, dt;

    /**
     * Default constructor for WeatherData class.
     * It initializes the weather data with default values.
     */
    public WeatherData() {
        location = null;
        temp = feelsLike = tempMin = tempMax = pressure = humidity = windSpeed = visibility = 0;
        main = description = icon = "";
        sunrise = sunset = dt = "";
    }

    /**
     * Constructor for WeatherData class.
     * It initializes the weather data using the provided JSON object and location data.
     *
     * @param json The JSON object containing the weather data.
     * @param location The location data for which the weather data is fetched.
     */
    public WeatherData(JsonObject json, LocationData location) {
        this.location = location;
        JsonObject main = json.getAsJsonObject("main");
        this.temp = main.get("temp").getAsFloat();
        this.feelsLike = main.get("feels_like").getAsFloat();
        this.tempMin = main.get("temp_min").getAsFloat();
        this.tempMax = main.get("temp_max").getAsFloat();
        this.pressure = main.get("pressure").getAsFloat();
        this.humidity = main.get("humidity").getAsFloat();

        this.visibility = json.get("visibility").getAsFloat();

        JsonObject wind = json.getAsJsonObject("wind");
        this.windSpeed = wind.get("speed").getAsFloat();

        JsonObject weather = json.getAsJsonArray("weather").get(0).getAsJsonObject();
        this.main = weather.get("main").getAsString();
        this.description = weather.get("description").getAsString();
        this.icon = weather.get("icon").getAsString();

        long timestamp;
        timestamp = json.get("dt").getAsLong();
        this.dt = DTConverter.convertToDateTime(timestamp);
        JsonObject sys = json.getAsJsonObject("sys");
        if (sys.has("sunrise") && sys.has("sunset")) {
            timestamp = sys.get("sunrise").getAsLong();
            this.sunrise = DTConverter.convertToDateTime(timestamp);
            timestamp = sys.get("sunset").getAsLong();
            this.sunset = DTConverter.convertToDateTime(timestamp);
        } else {
            this.sunrise = this.sunset = "N/A";
        }
    }

    /**
     * Method to display the weather details in the console.
     * It prints the location, temperature, humidity, wind speed, visibility, and weather description.
     */
    public void displayDetails() {
        System.out.println("City: " + location.city + ", " + location.country);
        System.out.println("Latitude: " + location.lat);
        System.out.println("Longitude: " + location.lon);
        System.out.println("Temperature: " + temp);
        System.out.println("Feels Like: " + feelsLike);
        System.out.println("Minimum Temperature: " + tempMin);
        System.out.println("Maximum Temperature: " + tempMax);
        System.out.println("Pressure: " + pressure);
        System.out.println("Humidity: " + humidity);
        System.out.println("Visibility: " + visibility);
        System.out.println("Wind Speed: " + windSpeed);
        System.out.println("Weather: " + main + ", " + description);
        System.out.println("Icon: " + icon);
        System.out.println("Sunrise: " + sunrise);
        System.out.println("Sunset: " + sunset);
        System.out.println("Fetch DateTime: " + dt);
    }
}