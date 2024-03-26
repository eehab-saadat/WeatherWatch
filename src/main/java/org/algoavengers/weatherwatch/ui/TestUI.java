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
//            location.displayDetails();

            System.out.println();
            WeatherData weatherData = WeatherForecaster.GetCurrentForecast(API_KEY, location);
            if (weatherData == null) {
                System.out.println("An error occurred while fetching the weather forecast.");
                return;
            }

//            weatherData.displayDetails();

            APData apData = APGetter.GetAPIData(API_KEY, location);
            if (apData == null) {
                System.out.println("An error occurred while fetching the air pollution data.");
                return;
            }
//            apData.displayDetails();

            WeatherData[] forecast = WeatherForecaster.GetPentaDayForecast(API_KEY, location);
            if (forecast == null) {
                System.out.println("An error occurred while fetching the 5-day weather forecast.");
                return;
            }
//
//            for (WeatherData data : forecast) {
//                data.displayDetails();
//            }

            CacheManager cacheManager = new CacheManager(new FileManager());
//            try {
//
//
//                cacheManager.cache.save(location, weatherData, apData);
//            } catch (Exception e) {
//                System.out.println("An error occurred while saving the data to the cache.");
//                System.out.println(e.getMessage());
//                return;
//            }

            try {


                Object[] obj = cacheManager.cache.find("Lahore");
                if (obj != null) {
                    LocationData loc = (LocationData) obj[0];
                    WeatherData wData = (WeatherData) obj[1];
                    APData aData = (APData) obj[2];
                    loc.displayDetails();
                    wData.displayDetails();
                    aData.displayDetails();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while fetching the data from the cache.");
                System.out.println(e.getMessage());
                return;
            }
            try {
                LocationData[] loc = cacheManager.cache.getTop5Locations();
                for (LocationData l : loc) {
                    l.displayDetails();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while fetching the top 5 locations from the cache.");
                System.out.println(e.getMessage());
                return;
            }
            try {
//            cacheManager.cache.delete(location.city);
                cacheManager.cache.deleteOutdatedRecords();

            }
            catch (Exception e) {
                System.out.println("An error occurred while deleting the data from the cache.");
                System.out.println(e.getMessage());
                return;
            }
        }
}
