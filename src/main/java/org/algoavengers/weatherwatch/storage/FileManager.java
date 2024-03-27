package org.algoavengers.weatherwatch.storage;

import com.google.gson.JsonObject;
import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager class implements CacheManagerInterface.
 * It provides methods to interact with a storage file.
 */
public class FileManager implements CacheManagerInterface {

    // Filepath for storage
    private final String filepath = "src\\main\\resources\\org\\algoavengers\\weatherwatch\\txt\\";


    public void save(LocationData location, WeatherData weatherData, APData apData) {
        // Check if the city already exists in the file
        if (find(location.city) != null) {
            System.out.println("The city already exists in the file. Skipping...");
            return;
        }

        try (FileWriter writer1 = new FileWriter(filepath + "WeatherDetails.txt", true);
             BufferedWriter bw1 = new BufferedWriter(writer1);
             FileWriter writer2 = new FileWriter(filepath + "APDetails.txt", true);
             BufferedWriter bw2 = new BufferedWriter(writer2)
        ) {
            bw1.write(location.city + ", " + apData.dt + ", " + location.country + ", " + location.lat + ", " + location.lon + ", " +
                    weatherData.temp + ", " + weatherData.feelsLike + ", " + weatherData.tempMin + ", " + weatherData.tempMax + ", " +
                    weatherData.pressure + ", " + weatherData.humidity + ", " + weatherData.windSpeed + ", " + weatherData.visibility + ", " +
                    weatherData.main + ", " + weatherData.description + ", " + weatherData.icon + ", " + weatherData.sunrise + ", " + weatherData.sunset);
            bw1.newLine();

            bw2.write(location.city + ", " + apData.dt + ", " + apData.aqi + ", " + location.country + ", " + location.lat + ", " + location.lon + ", " +
                    apData.co + ", " + apData.no + ", " + apData.no2 + ", " + apData.o3 + ", " + apData.so2 + ", " + apData.pm2_5 + ", " + apData.pm10 + ", " + apData.nh3 + ", " + apData.comment);
            bw2.newLine();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred. " + e.getMessage());
        }
    }
    /**
     * Method to delete city data from storage file.
     * @param city Name of the city to be deleted.
     */
    public void delete(String city) {
        try {
            File weatherDetailsFile = new File(filepath + "WeatherDetails.txt");
            File apDetailsFile = new File(filepath + "APDetails.txt");
            File tempWeatherDetailsFile = new File(filepath + "tempWeatherDetails.txt");
            File tempAPDetailsFile = new File(filepath + "tempAPDetails.txt");

            BufferedReader weatherDetailsReader = new BufferedReader(new FileReader(weatherDetailsFile));
            BufferedReader apDetailsReader = new BufferedReader(new FileReader(apDetailsFile));
            BufferedWriter tempWeatherDetailsWriter = new BufferedWriter(new FileWriter(tempWeatherDetailsFile));
            BufferedWriter tempAPDetailsWriter = new BufferedWriter(new FileWriter(tempAPDetailsFile));

            String currentLine;

            // Read lines from WeatherDetails.txt and APDetails.txt, skip the ones that start with the city name
            while ((currentLine = weatherDetailsReader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (!parts[0].trim().equals(city)) { // If the record is not for the city to be deleted
                    tempWeatherDetailsWriter.write(currentLine + System.getProperty("line.separator"));
                }
            }

            while ((currentLine = apDetailsReader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (!parts[0].trim().equals(city)) { // If the record is not for the city to be deleted
                    tempAPDetailsWriter.write(currentLine + System.getProperty("line.separator"));
                }
            }

            // Close readers and writers
            weatherDetailsReader.close();
            apDetailsReader.close();
            tempWeatherDetailsWriter.close();
            tempAPDetailsWriter.close();

            // Delete original files
            if (!weatherDetailsFile.delete() || !apDetailsFile.delete()) {
                System.out.println("Failed to delete the original files.");
                return;
            }

            // Rename the temporary files to the original file names
            if (!tempWeatherDetailsFile.renameTo(weatherDetailsFile) || !tempAPDetailsFile.renameTo(apDetailsFile)) {
                System.out.println("Failed to rename the temporary files.");
                return;
            }

            System.out.println("Record deleted successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Method to find latitude and longitude of a given city.
     * @param city Name of the city to be found.
     * @return Array of floats where the first element is the latitude and the second is the longitude.
     */
    public Object[] find(String city) {
        try (BufferedReader br1 = new BufferedReader(new FileReader(filepath + "WeatherDetails.txt"));
             BufferedReader br2 = new BufferedReader(new FileReader(filepath + "APDetails.txt"))) {
            String currentLine;
            LocationData locationData = null;
            WeatherData weatherData = null;
            APData apData = null;

            // Read lines from WeatherDetails.txt and fill LocationData and WeatherData objects
            while ((currentLine = br1.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts[0].trim().equalsIgnoreCase(city)) { // Use case-insensitive comparison
                    locationData = new LocationData(parts[0].trim(), parts[2].trim(), Float.parseFloat(parts[3].trim()), Float.parseFloat(parts[4].trim()));
                    weatherData = new WeatherData(); // initializing the object with default constructor where location is null
                    weatherData.setLocation(locationData);
                    weatherData.temp = Float.parseFloat(parts[5].trim());
                    // Fill the rest of the weatherData fields
                    weatherData.feelsLike = Float.parseFloat(parts[6].trim());
                    weatherData.tempMin = Float.parseFloat(parts[7].trim());
                    weatherData.tempMax = Float.parseFloat(parts[8].trim());
                    weatherData.pressure = Float.parseFloat(parts[9].trim());
                    weatherData.humidity = Float.parseFloat(parts[10].trim());
                    weatherData.windSpeed = Float.parseFloat(parts[11].trim());
                    weatherData.visibility = Float.parseFloat(parts[12].trim());

                    weatherData.main = parts[13].trim();
                    System.out.println(weatherData.main);


                    weatherData.description = parts[14].trim();
                    weatherData.icon = parts[15].trim();
                    weatherData.sunrise = parts[16].trim();
                    weatherData.sunset = parts[17].trim();
                    break;
                }
            }

            // Read lines from APDetails.txt and fill APData objects
            while ((currentLine = br2.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts[0].trim().equalsIgnoreCase(city)) { // Use case-insensitive comparison
                    apData = new APData();
                    apData.dt = parts[1].trim();
                    apData.aqi = Integer.parseInt(parts[2].trim());
                    apData.setLocation(locationData);
                    // Fill the rest of the apData fields
                    apData.co = Float.parseFloat(parts[6].trim());
                    apData.no = Float.parseFloat(parts[7].trim());
                    apData.no2 = Float.parseFloat(parts[8].trim());
                    apData.o3 = Float.parseFloat(parts[9].trim());
                    apData.so2 = Float.parseFloat(parts[10].trim());
                    apData.pm2_5 = Float.parseFloat(parts[11].trim());
                    apData.pm10 = Float.parseFloat(parts[12].trim());
                    apData.nh3 = Float.parseFloat(parts[13].trim());
                    apData.comment = parts[14].trim();
                    break;
                }
            }

            if (locationData != null && weatherData != null && apData != null) {
                return new Object[]{locationData, weatherData, apData};
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return null; // City not found or error occurred
    }
    /**
     * Method to retrieve latest five entries from storage file.
     * @return Array of strings where each string represents a city with its latitude and longitude.
     */
    public LocationData[] getTop5Locations() {
        try {
            File inputFile = new File(filepath + "storage.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine;
            int count = 0;

            // Count total number of lines in the file
            while ((currentLine = reader.readLine()) != null) {
                count++;
            }

            // Close and reopen reader to read from the beginning
            reader.close();
            reader = new BufferedReader(new FileReader(inputFile));

            String[] parts;
            List<LocationData> latestFive = new ArrayList<>();
            int count1 = 0;

            // Read lines from file and add the latest five to the list
            while ((currentLine = reader.readLine()) != null) {
                count1++;
                if (count1 > count - 5) {
                    parts = currentLine.split(",");
                    String city = parts[0].trim();
                    float lat = Float.parseFloat(parts[1].trim());
                    float lon = Float.parseFloat(parts[2].trim());
                    latestFive.add(new LocationData(city, "", lat, lon)); // Assuming country is not available in the file
                }
            }

            // Convert list to array
            LocationData[] latestFiveArray = latestFive.toArray(new LocationData[0]);
            return latestFiveArray;
        } catch (IOException e) {
            System.out.println("An error occurred. " + e.getMessage());
            return null;
        }


    }


    public void deleteOutdatedRecords() {
        try {
            File weatherDetailsFile = new File(filepath + "WeatherDetails.txt");
            File apDetailsFile = new File(filepath + "APDetails.txt");
            File tempWeatherDetailsFile = new File(filepath + "tempWeatherDetails.txt");
            File tempAPDetailsFile = new File(filepath + "tempAPDetails.txt");

            BufferedReader weatherDetailsReader = new BufferedReader(new FileReader(weatherDetailsFile));
            BufferedReader apDetailsReader = new BufferedReader(new FileReader(apDetailsFile));
            BufferedWriter tempWeatherDetailsWriter = new BufferedWriter(new FileWriter(tempWeatherDetailsFile));
            BufferedWriter tempAPDetailsWriter = new BufferedWriter(new FileWriter(tempAPDetailsFile));

            String currentLine;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentTimeMinus24Hours = LocalDateTime.now().minusHours(24);

            // Read lines from WeatherDetails.txt and APDetails.txt, skip the ones older than 24 hours
            while ((currentLine = weatherDetailsReader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                LocalDateTime dt = LocalDateTime.parse(parts[1].trim(), formatter);
                if (dt.isAfter(currentTimeMinus24Hours)) { // If the record is not older than 24 hours
                    tempWeatherDetailsWriter.write(currentLine + System.getProperty("line.separator"));
                }
            }

            while ((currentLine = apDetailsReader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                LocalDateTime dt = LocalDateTime.parse(parts[1].trim(), formatter);
                if (dt.isAfter(currentTimeMinus24Hours)) { // If the record is not older than 24 hours
                    tempAPDetailsWriter.write(currentLine + System.getProperty("line.separator"));
                }
            }

            // Close readers and writers
            weatherDetailsReader.close();
            apDetailsReader.close();
            tempWeatherDetailsWriter.close();
            tempAPDetailsWriter.close();

            // Delete original files
            if (!weatherDetailsFile.delete() || !apDetailsFile.delete()) {
                System.out.println("Failed to delete the original files.");
                return;
            }

            // Rename the temporary files to the original file names
            if (!tempWeatherDetailsFile.renameTo(weatherDetailsFile) || !tempAPDetailsFile.renameTo(apDetailsFile)) {
                System.out.println("Failed to rename the temporary files.");
                return;
            }

            System.out.println("Outdated records deleted successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred. " + e.getMessage());
        }
    }



}
