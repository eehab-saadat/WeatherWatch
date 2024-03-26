package org.algoavengers.weatherwatch.ui;

import org.algoavengers.weatherwatch.models.*;
import org.algoavengers.weatherwatch.services.apis.*;
import org.algoavengers.weatherwatch.storage.*;

import java.util.Scanner;

public class TestUI implements DisplayInterface {

        @Override
        public void run(String API_KEY) {
            System.out.println("Running TestUI");
            System.out.println("Enter city name: ");
            Scanner scanner = new Scanner(System.in);
            String city = scanner.nextLine();
            scanner.close();

            LocationData location = Geocoder.geocode(API_KEY, city);
            if (location == null) {
                System.out.println("An error occurred while fetching the coordinates.");
                return;
            }
            location.displayDetails();

            System.out.println();
            WeatherData weatherData = WeatherForecaster.GetCurrentForecast(API_KEY, location);
            if (weatherData == null) {
                System.out.println("An error occurred while fetching the weather forecast.");
                return;
            }

            APData apData = APGetter.GetAPIData(API_KEY, location);
            if (apData == null) {
                System.out.println("An error occurred while fetching the air pollution data.");
                return;
            }

            WeatherData[] forecast = WeatherForecaster.GetPentaDayForecast(API_KEY, location);
            if (forecast == null) {
                System.out.println("An error occurred while fetching the 5-day weather forecast.");
                return;
            }

            CacheManager cacheManager = new CacheManager(new DBManager());
            cacheManager.cache.save(location, weatherData, apData);
            cacheManager.cache.find(location.city);
            cacheManager.cache.getTop5Locations();
            cacheManager.cache.delete(location.city);
            cacheManager.cache.deleteOutdatedRecords();
        }
}
