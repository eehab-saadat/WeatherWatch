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

    // Data members
    private WeatherData currentWeather;
    private LocationData location;
    private WeatherData[] forecast;
    private APData APdata;

    @Override
    public void run(String API_KEY) {
        System.out.println("Welcome to the Location App!");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Get saved locations");
            System.out.println("2. Search by name");
            System.out.println("3. Search by coordinates");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1/2/3/4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Placeholder for getSavedLocations() method
                    break;
                case 2:
                    searchByName(API_KEY, scanner);
                    break;
                case 3:
                    // Placeholder for searchByCoordinates() method
                    break;
                case 4:
                    System.out.println("Exiting. Thank you!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }
    }

    private void searchByName(String API_KEY, Scanner scanner) {
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine();
        try {
            location = Geocoder.geocode(API_KEY, cityName);
            if (location == null) {
                System.out.println("Location not found.");
                return;
            }

            currentWeather = WeatherForecaster.GetCurrentForecast(API_KEY, location);
            if (currentWeather == null) {
                System.out.println("Error fetching weather data.");
                return;
            }

            forecast = WeatherForecaster.GetPentaDayForecast(API_KEY, location);
            if (forecast == null) {
                System.out.println("Error fetching 5-day forecast.");
                return;
            }

            APdata = APGetter.GetAPIData(API_KEY, location);
            if (APdata == null) {
                System.out.println("Error fetching air pollution data.");
                return;
            }

            System.out.println("Data fetched successfully for " + cityName + ".");
            displayActionMenu(scanner);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void displayActionMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAction Menu:");
            System.out.println("1. Get current weather");
            System.out.println("2. Basic information");
            System.out.println("3. Get sunset/sunrise");
            System.out.println("4. Get 5-day forecast");
            System.out.println("5. Get AQI");
            System.out.println("6. Get air pollution info");
            System.out.println("7. Save location");
            System.out.println("8. Set trigger for AQI");
            System.out.println("9. Set trigger for weather condition");
            System.out.println("10. Back to Main Menu");

            System.out.print("Enter your choice (1-10): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    getCurrentWeather();
                    break;
                case 2:
                    getBasicInformation();
                    break;
                case 3:
                    getSunsetSunrise();
                    break;
                case 4:
                    get5DayForecast();
                    break;
                case 5:
                    getAQI();
                    break;
                case 6:
                    getAirPollutionInfo();
                    break;
               /*  case 7:
                 saveLocation();
                 break;
                 case 8:
                   setAQITrigger();
                   break;
                 case 9:
                   setWeatherConditionTrigger();
                  break;
                  */
                case 10:
                    return; // Return to Main Menu
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 10.");
            }
        }
    }

    private void getCurrentWeather() {
        if (currentWeather != null) {
            // Print current weather data
            System.out.println("Current Weather:");
            System.out.println("Temperature: " + currentWeather.temp);
            System.out.println("Feels Like: " + currentWeather.feelsLike);
            System.out.println("Main: " + currentWeather.main);
            System.out.println("Description: " + currentWeather.description);
            // Print more data as needed
        } else {
            System.out.println("No current weather data available.");
        }
    }

    private void getBasicInformation() {
        if (currentWeather != null) {
            // Print basic information
            System.out.println("Basic Information:");
            System.out.println("Temperature: " + currentWeather.temp);
            System.out.println("Feels Like: " + currentWeather.feelsLike);
            System.out.println("Main: " + currentWeather.main);
            System.out.println("Description: " + currentWeather.description);
            System.out.println("Minimum Temperature: " + currentWeather.tempMin);
            System.out.println("Maximum Temperature: " + currentWeather.tempMax);
            // Print more data as needed
        } else {
            System.out.println("No current weather data available.");
        }
    }

    private void getSunsetSunrise() {
        if (currentWeather != null) {
            // Print sunset and sunrise times
            System.out.println("Sunset Time: " + currentWeather.sunset);
            System.out.println("Sunrise Time: " + currentWeather.sunrise);
        } else {
            System.out.println("No current weather data available.");
        }
    }

    private void get5DayForecast() {
        if (forecast != null) {
            // Print 5-day forecast data
            System.out.println("5-Day Forecast:");
            for (WeatherData data : forecast) {
                System.out.println("Date: " + data.dt);
                System.out.println("Temperature: " + data.temp);
                // Print more forecast data as needed
            }
        } else {
            System.out.println("No 5-day forecast data available.");
        }
    }

    private void getAQI() {
        if (APdata != null) {
            // Print AQI data
            System.out.println("Air Quality Index (AQI): " + APdata.aqi);
            System.out.println("Comment: " + APdata.comment);
            // Print more AQI data as needed
        } else {
            System.out.println("No air quality data available.");
        }
    }

    private void getAirPollutionInfo() {
        if (APdata != null) {
            // Print air pollution data
            System.out.println("Air Pollution Information:");
            System.out.println("Air Quality Index (AQI): " + APdata.aqi);
            System.out.println("Comment: " + APdata.comment);
        }
    }
}

