package org.algoavengers.weatherwatch.utils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class is used to convert country codes to their corresponding capital cities.
 */
public class CountryCodeConverter {

    /**
     * The path to the CSV file containing country codes and their corresponding capital cities.
     */
    public static String filePath = "src/main/resources/org/algoavengers/weatherwatch/data/country-capitals.csv";

    /**
     * This method takes a country code as input and returns the corresponding capital city.
     * It reads the CSV file line by line and checks if the country code matches the input.
     * If a match is found, it returns the corresponding capital city.
     * If no match is found, it returns "N/A".
     *
     * @param code The country code to be converted to a capital city.
     * @return The capital city corresponding to the input country code, or "N/A" if no match is found.
     */
    public static String getCapitalCity(String code) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(code)) {
                    return values[1];
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while converting country code: " + e.getMessage());
        }
        return "N/A";
    }
}