package org.algoavengers.weatherwatch.ui;


import java.util.Scanner;

import com.google.gson.JsonObject;
import org.algoavengers.weatherwatch.apis.Geocoder;
import org.algoavengers.weatherwatch.apis.WeatherForecaster;

public class TerminalUI implements DisplayInterface {
    @Override
    public void run(String API_KEY) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter city name: ");
            String city = scanner.nextLine();
            scanner.close();

            float[] coordinates = Geocoder.geocode(API_KEY, city);
            if (coordinates == null) {
                System.out.println("An error occurred while fetching the coordinates.");
                return;
            }
            System.out.println("Latitude: " + coordinates[0]);
            System.out.println("Longitude: " + coordinates[1]);
            JsonObject forecast = WeatherForecaster.CurrentForecast(API_KEY, coordinates[0], coordinates[1]);
            if (forecast == null) {
                System.out.println("An error occurred while fetching the weather forecast.");
                return;
            }
            System.out.println("Weather: " + forecast.toString());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
