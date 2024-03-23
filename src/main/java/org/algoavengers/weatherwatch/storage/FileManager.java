package org.algoavengers.weatherwatch.storage;

import java.io.*;
public class FileManager implements CacheManagerInterface {
    public void save(String city, float lat, float lon) {
        try {
            FileWriter writer = new FileWriter("C:\\Users\\KHAN\\Desktop\\SDA PROJECT\\Proj file\\WeatherWatch\\src\\main\\java\\org\\algoavengers\\weatherwatch\\storage\\storage.txt", true);
            BufferedWriter bw = new BufferedWriter(writer);
            String coma = ", ";
            bw.write(city);
            bw.write(coma);
            bw.write(String.valueOf(lat));
            bw.write(coma);
            bw.write(String.valueOf(lon));
            bw.newLine();
            bw.flush();
            bw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void delete(String city) {
        try {
            File inputFile = new File("C:\\Users\\KHAN\\Desktop\\SDA PROJECT\\Proj file\\WeatherWatch\\src\\main\\java\\org\\algoavengers\\weatherwatch\\storage\\storage.txt");
            File tempFile = new File("C:\\Users\\KHAN\\Desktop\\SDA PROJECT\\Proj file\\WeatherWatch\\src\\main\\java\\org\\algoavengers\\weatherwatch\\storage\\tempStorage.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = city;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToRemove)) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            // Close readers and writers
            reader.close();
            writer.close();

            // Delete original file
            if (!inputFile.delete()) {
                System.out.println("Failed to delete the original file.");
                return;
            }

            // Rename the temporary file to the original file
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Failed to rename the temporary file.");
                return;
            }

            System.out.println("Record deleted successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public float[] find(String city) {
        try {
            FileReader inputFile = new FileReader("C:\\Users\\KHAN\\Desktop\\SDA PROJECT\\Proj file\\WeatherWatch\\src\\main\\java\\org\\algoavengers\\weatherwatch\\storage\\storage.txt");
            BufferedReader br = new BufferedReader(inputFile);
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts.length >= 3 && parts[0].trim().equals(city)) {
                    float lat = Float.parseFloat(parts[1].trim());
                    float lon = Float.parseFloat(parts[2].trim());
                    return new float[]{lat, lon};
                }
            }
            br.close(); // Close the BufferedReader
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null; // City not found or error occurred
    }
}


