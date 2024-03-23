package org.algoavengers.weatherwatch.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager implements CacheManagerInterface {
    private final String filepath = "src/main/java/org/algoavengers/weatherwatch/storage/";
    public void save(String city, float lat, float lon) {
        try (FileWriter writer = new FileWriter(filepath + "storage.txt", true);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(city + ", " + lat + ", " + lon);
            bw.newLine();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred. " + e.getMessage());
        }
    }

    public void delete(String city) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath + "storage.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filepath + "temp.txt"))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.trim().startsWith(city)) {
                    writer.write(currentLine + System.lineSeparator());
                }
            }

            File inputFile = new File(filepath + "storage.txt");
            File tempFile = new File(filepath + "temp.txt");

            if (!inputFile.delete()) {
                System.out.println("Failed to delete the original file.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Failed to rename the temporary file.");
                return;
            }

            System.out.println("Record deleted successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public float[] find(String city) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath + "storage.txt"))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                int firstComma = currentLine.indexOf(',');
                if (firstComma > 0 && currentLine.substring(0, firstComma).trim().equals(city)) {
                    String[] parts = currentLine.split(",");
                    float lat = Float.parseFloat(parts[1].trim());
                    float lon = Float.parseFloat(parts[2].trim());
                    return new float[]{lat, lon};
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return null; // City not found or error occurred
    }
}