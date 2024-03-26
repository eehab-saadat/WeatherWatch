package org.algoavengers.weatherwatch.ui;


import java.util.Scanner;

import com.google.gson.JsonObject;
import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;
import org.algoavengers.weatherwatch.services.apis.APGetter;
import org.algoavengers.weatherwatch.services.apis.Geocoder;
import org.algoavengers.weatherwatch.services.apis.WeatherForecaster;

/**
 * TerminalUI class implements the DisplayInterface.
 * It provides a terminal-based user interface for the weather watch application.
 */
public class TerminalUI implements DisplayInterface {

    /**
     * The run method is the entry point for the TerminalUI.
     * It prompts the user for a city name, fetches the location data, current weather forecast,
     * 5-day weather forecast, and air pollution data for the entered city, and displays the data.
     *
     * @param API_KEY The API key to be used for the API calls.
     */
    @Override
    public void run(String API_KEY) {
        try {
            // Create a Scanner object for user input
            Scanner scanner = new Scanner(System.in);

            // Prompt the user for a city name
            System.out.print("Enter city name: ");
            String city = scanner.nextLine();

            // Close the Scanner object
            scanner.close();

            // Fetch the location data for the entered city
            LocationData location = Geocoder.geocode(API_KEY, city);

            // If the location data is null, print an error message and return
            if (location == null) {
                System.out.println("An error occurred while fetching the coordinates.");
                return;
            }

            // Display the location data
            location.displayDetails();

            // Fetch the current weather forecast for the location
            System.out.println();
            System.out.println("Fetching weather forecast...");
            System.out.println();
            WeatherData weatherData = WeatherForecaster.GetCurrentForecast(API_KEY, location);

            // If the weather data is null, print an error message and return
            if (weatherData == null) {
                System.out.println("An error occurred while fetching the weather forecast.");
                return;
            }

            // Display the current weather forecast
            System.out.println("-----------------------");
            System.out.println("Current Weather Forecast");
            weatherData.displayDetails();

            // Fetch the 5-day weather forecast for the location
            System.out.println();
            System.out.println("Fetching weather forecast for the next 5 days...");
            System.out.println();
            WeatherData[] weatherArray = WeatherForecaster.GetPentaDayForecast(API_KEY, location);

            // If the 5-day weather forecast is null, print an error message and return
            if (weatherArray == null) {
                System.out.println("An error occurred while fetching the weather forecast.");
                return;
            }

            // Display the 5-day weather forecast
            System.out.println("-----------------------");
            System.out.println("Weather Forecast for the next 5 days:");
            for (WeatherData weatherDataItem : weatherArray) {
                weatherDataItem.displayDetails();
                System.out.println("-----------------------");
            }

            // Fetch the air pollution data for the location
            System.out.println();
            System.out.println("Fetching Air Pollution Data...");
            System.out.println();
            APData apData = APGetter.GetAPIData(API_KEY, location);

            // If the air pollution data is null, print an error message and return
            if (apData == null) {
                System.out.println("An error occurred while fetching the air pollution data.");
                return;
            }

            // Display the air pollution data
            System.out.println("-----------------------");
            System.out.println("Air Polution Data:");
            apData.displayDetails();

            /*
            CacheManagerInterface cacheManager = new DBManager();
            cacheManager.save(city, coordinates[0], coordinates[1]);
            coordinates = cacheManager.find("Tokyo");
            if (coordinates != null) {
                System.out.println("Latitude: " + coordinates[0]);
                System.out.println("Longitude: " + coordinates[1]);
            }
            cacheManager.delete("Delhi");
            */

        } catch (Exception e) {
            // Print any exceptions that occur
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}