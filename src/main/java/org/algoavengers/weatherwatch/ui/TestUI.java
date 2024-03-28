package org.algoavengers.weatherwatch.ui;

import org.algoavengers.weatherwatch.models.*;
import org.algoavengers.weatherwatch.services.WeatherWatchService;
import org.algoavengers.weatherwatch.services.apis.*;
import org.algoavengers.weatherwatch.storage.*;

import java.util.Objects;
import java.util.Scanner;

public class TestUI implements DisplayInterface {

        @Override
        public void run(String API_KEY) {
            System.out.println("Running TestUI");
            FileManager fileManager = new FileManager();
            fileManager.clear();
//            WeatherWatchService wws = new WeatherWatchService();
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Enter the city name: ");
//            String city = scanner.nextLine();
//            scanner.close();
//
//            LocationData location = wws.cityToCoords(city);
//            if (location == null) {
//                System.out.println("An error occurred while fetching the city name.");
//                return;
//            }
//            Object[] weatherData = wws.fetchData(location);
//            if (weatherData == null) {
//                System.out.println("An error occurred while fetching the weather data.");
//                return;
//            }
//            LocationData locationData = (LocationData) weatherData[0];
//            WeatherData weather = (WeatherData) weatherData[1];
//            APData ap = (APData) weatherData[2];
//            WeatherData[] forecast = (WeatherData[]) weatherData[3];
//
//            locationData.displayDetails();
//            weather.displayDetails();
//            ap.displayDetails();
//            for (WeatherData day : forecast) {
//                day.displayDetails();
//            }
        }
}
