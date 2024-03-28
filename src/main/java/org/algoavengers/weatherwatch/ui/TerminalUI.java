package org.algoavengers.weatherwatch.ui;


import java.util.Scanner;

import com.google.gson.JsonObject;
import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;
import org.algoavengers.weatherwatch.services.WeatherWatchService;
import org.algoavengers.weatherwatch.services.apis.APGetter;
import org.algoavengers.weatherwatch.services.apis.Geocoder;
import org.algoavengers.weatherwatch.services.apis.WeatherForecaster;
import org.algoavengers.weatherwatch.storage.CacheManager;
import org.algoavengers.weatherwatch.storage.DBManager;
import java.io.IOException;

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
    private WeatherWatchService weather = new WeatherWatchService();

    @Override
    public void run(String API_KEY) {
        System.out.println("Welcome to the Location App!");
        String triggerUpdate = weather.getTrigger();
        if (triggerUpdate != null) {
            System.out.println("Trigger update: " + triggerUpdate);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Get saved locations");
            System.out.println("2. Search by name");
            System.out.println("3. Search by coordinates");
            System.out.println("4. Credits");
            System.out.println("5. Exit");

            System.out.print("Enter your choice (1/2/3/4/5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                   WeatherWatchService cacheManager = new WeatherWatchService();
                    LocationData[] locationArr = cacheManager.getSavedLocations();
                    break;
                case 2:
                    searchByName(API_KEY, scanner);
                    break;
                case 3:
                    searchByCoordinates(API_KEY, scanner);
                    break;
                case 4:
                    displayCredits(scanner);
                    break;
                case 5:
                    System.out.println("Exiting. Thank you!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, 4 or 5.");
            }
        }
    }
    private void pauseScreen(Scanner scanner) {
        // Pause screen until user presses Enter
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
   private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }
    private void displayCredits(Scanner scanner) {

        System.out.println("***************************************************");
        System.out.println("*                                                 *");
        System.out.println("*            WeatherWatch Application             *");
        System.out.println("*                                                 *");
        System.out.println("*         Developed by the AlgoAvengers           *");
        System.out.println("*                                                 *");
        System.out.println("*     Credits to the amazing team members:        *");
        System.out.println("*                                                 *");
        System.out.println("*       - Eehab Saadat                            *");
        System.out.println("*       - Fayyez Farrukh                          *");
        System.out.println("*       - M.Ahmad Karim                           *");
        System.out.println("*       - Zain Iqbal                              *");
        System.out.println("*       - Arahim Qaiser                           *");
        System.out.println("*       - Ammar Khan                              *");
        System.out.println("*                                                 *");
        System.out.println("*        Instructor:                              *");
        System.out.println("*                                                 *");
        System.out.println("*       - Aamir Iqbal                             *");
        System.out.println("*                                                 *");
        System.out.println("*        Teaching Assistant:                      *");
        System.out.println("*                                                 *");
        System.out.println("*       - Moeez Ali                               *");
        System.out.println("*                                                 *");
        System.out.println("***************************************************");
        pauseScreen(scanner);
    }
    private void searchByName(String API_KEY, Scanner scanner) {
        try {
            System.out.print("Enter city name: ");
            String cityName = scanner.nextLine();
            LocationData location = weather.cityToCoords(cityName);
            if (location != null) {
                Object[] data = weather.fetchData(location);
                if (data != null) {
                    this.location = (LocationData) data[0];
                    this.currentWeather = (WeatherData) data[1];
                    this.APdata = (APData) data[2];
                    this.forecast = (WeatherData[]) data[3];
                    System.out.println("Data fetched successfully for " + cityName + ".");
                    displayActionMenu(scanner);
                } else {
                    System.out.println("Error fetching data for " + cityName + ".");
                }
            } else {
                System.out.println("Location not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void searchByCoordinates(String API_KEY, Scanner scanner) {
        try {
            System.out.print("Enter latitude: ");
            float latitude = scanner.nextFloat();
            scanner.nextLine();
            System.out.print("Enter longitude: ");
            float longitude = scanner.nextFloat();
            scanner.nextLine();
            LocationData location = weather.coordsToCity(latitude, longitude);
            if (location != null) {
                Object[] data = weather.fetchData(location);
                if (data != null) {
                    this.location = (LocationData) data[0];
                    this.currentWeather = (WeatherData) data[1];
                    this.APdata = (APData) data[2];
                    this.forecast = (WeatherData[]) data[3];
                    System.out.println("Data fetched successfully for coordinates: " + latitude + ", " + longitude);
                    displayActionMenu(scanner);
                } else {
                    System.out.println("Error fetching data for coordinates: " + latitude + ", " + longitude);
                }
            } else {
                System.out.println("Location not found.");
            }
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
                    getCurrentWeather(scanner);
                    break;
                case 2:
                    getBasicInformation(scanner);
                    break;
                case 3:
                    getSunsetSunrise(scanner);
                    break;
                case 4:
                    get5DayForecast(scanner);
                    break;
                case 5:
                    getAQI(scanner);
                    break;
                case 6:
                    getAirPollutionInfo(scanner);
                    break;
                 case 7:
                 saveLocation(scanner);
                 break;
                   case 8:
                       setWeatherConditionTrigger();
                   break;
                 case 9:
                     return; // Return to Main Menu
                  break;

                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 10.");
            }
        }
    }

    private void getCurrentWeather(Scanner scanner) {
        if (currentWeather != null) {
            // Print current weather data
            System.out.println("Current Weather:");
            System.out.println("Temperature: " + currentWeather.temp);
            System.out.println("Feels Like: " + currentWeather.feelsLike);
            System.out.println("Main: " + currentWeather.main);
            System.out.println("Description: " + currentWeather.description);
            pauseScreen(scanner);

        } else {
            System.out.println("No current weather data available.");
        }
    }

    private void getBasicInformation(Scanner scanner) {
        if (currentWeather != null) {
            // Print basic information
            System.out.println("Basic Information:");
            System.out.println("Temperature: " + currentWeather.temp);
            System.out.println("Feels Like: " + currentWeather.feelsLike);
            System.out.println("Main: " + currentWeather.main);
            System.out.println("Description: " + currentWeather.description);
            System.out.println("Minimum Temperature: " + currentWeather.tempMin);
            System.out.println("Maximum Temperature: " + currentWeather.tempMax);
            pauseScreen(scanner);
        } else {
            System.out.println("No current weather data available.");
        }
    }

    private void getSunsetSunrise(Scanner scanner) {
        if (currentWeather != null) {
            // Print sunset and sunrise times
            System.out.println("Sunset Time: " + currentWeather.sunset);
            System.out.println("Sunrise Time: " + currentWeather.sunrise);
            pauseScreen(scanner);
        } else {
            System.out.println("No current weather data available.");
        }
    }

    private void get5DayForecast(Scanner scanner) {
        if (forecast != null) {
            // Print 5-day forecast data
            System.out.println("5-Day Forecast:");
            for (WeatherData data : forecast) {
                System.out.println("Date: " + data.dt);
                System.out.println("Main: " + data.main);
                System.out.println("Description: " + data.description);
                System.out.println("Temperature: " + data.temp);
                System.out.println("min temp: " + data.tempMin);
                System.out.println("max temp: " + data.tempMax);
                pauseScreen(scanner);
            }
        } else {
            System.out.println("No 5-day forecast data available.");
        }
    }

    private void getAQI(Scanner scanner) {
        if (APdata != null) {
            // Print AQI data
            System.out.println("Air Quality Index (AQI): " + APdata.aqi);
            System.out.println("Comment: " + APdata.comment);
            pauseScreen(scanner);
        } else {
            System.out.println("No air quality data available.");
        }
    }

    private void getAirPollutionInfo(Scanner scanner) {
        if (APdata != null) {
            // Print air pollution data
            getAQI(scanner);
            System.out.println("Carbon Monoxide: " + APdata.co);
            System.out.println("Nitrogen Monoxide: " + APdata.no);
            System.out.println("Nitrogen Dioxide: " + APdata.no2);
            System.out.println("Suplhur: " + APdata.so2);
            System.out.println("PM 2.5: " + APdata.pm2_5);
            System.out.println("PM 10: " + APdata.pm10);
            pauseScreen(scanner);
        }
    }
    private void saveLocation(Scanner scanner) {
        if (location != null) {
            weather.saveLocation(location);
            System.out.println("Location saved successfully.");
        } else {
            System.out.println("No location data available to save.");
        }
        pauseScreen(scanner);
    }
    private void setWeatherConditionTrigger(Scanner scanner) {
        if (location != null) {
            System.out.println("Choose a weather condition trigger:");
            System.out.println("1. Heat Wave");
            System.out.println("2. Snow");
            System.out.println("3. Hurricane");
            System.out.print("Enter your choice (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    weather.setHeatWaveTrigger(location);
                    System.out.println("Trigger for heat wave set successfully in " + location.city + " for the next 7 days.");
                    break;
                case 2:
                    weather.setSnowTrigger(location);
                    System.out.println("Trigger for snow set successfully in " + location.city + " for the next 7 days.");
                    break;
                case 3:
                    weather.setHurricaneTrigger(location);
                    System.out.println("Trigger for hurricane set successfully in " + location.city + " for the next 7 days.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        } else {
            System.out.println("No location data available to set trigger.");
        }
        pauseScreen(scanner);
    }

}