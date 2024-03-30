package org.algoavengers.weatherwatch.utils;

import java.io.BufferedReader;
import java.io.FileReader;
public class CountryCodeConverter {
    public static String filePath = "src/main/resources/org/algoavengers/weatherwatch/data/country-capitals.csv";
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
