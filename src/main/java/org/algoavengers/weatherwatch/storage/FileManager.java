package org.algoavengers.weatherwatch.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager class is responsible for managing the storage of city data.
 * It provides methods to save, delete, and find city data in a text file.
 */
public class FileManager {
    // The path to the file where city data is stored
    private final String filepath = "src/main/java/org/algoavengers/weatherwatch/storage/";

    /**
     * Saves the given city data to the file.
     * If the city is already saved, it prints a message and returns.
     *
     * @param city The name of the city
     * @param lat The latitude of the city
     * @param lon The longitude of the city
     */
    public void save(String city, float lat, float lon) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {

            reader = new BufferedReader(new FileReader(filepath + "storage.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^" + city + ",.*")) {
                    System.out.println("The city is already saved.");
                    return;
                }
            }

            writer = new BufferedWriter(new FileWriter(filepath + "storage.txt", true));
            writer.write(city + ", " + lat + ", " + lon + "\n");

        }
        catch (IOException e) {

            System.out.println("An error occurred while trying to write to the file.");
            e.printStackTrace();
        }
        finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while trying to close the file.");
                    e.printStackTrace();
                }
        }
    }

    /**
     * Deletes the given city data from the file.
     * If the city does not exist in the file, it prints a message.
     *
     * @param city The name of the city
     */
    public void delete(String city) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        boolean cityFound = false;
        try {

            reader = new BufferedReader(new FileReader(filepath + "storage.txt"));

            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).matches("^" + city + ",.*")) {
                    lines.remove(i);
                    cityFound = true;
                    break;
                }
            }

            reader.close();

            writer = new BufferedWriter(new FileWriter(filepath + "storage.txt"));

            for (String l : lines) {
                writer.write(l + "\n");
            }

            if (!cityFound) {
                System.out.println("The city does not exist in the file.");
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred while trying to delete the city from the file.");
            e.printStackTrace();

        }
        finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
                catch (IOException e) {
                    System.out.println("An error occurred while trying to close the file.");
                    e.printStackTrace();
                }
        }
    }

    /**
     * Finds the given city data in the file.
     * If the city does not exist in the file, it prints a message and returns an empty array.
     *
     * @param city The name of the city
     */
    public void find(String city) {
        BufferedReader reader = null;
        boolean cityFound = false;
        try {

            reader = new BufferedReader(new FileReader(filepath + "storage.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^" + city + ",.*")) {
                    System.out.println(line);
                    cityFound = true;
                    break;
                }
            }

            if (!cityFound) {
                System.out.println("The city does not exist in the file.");
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred while trying to find the city in the file.");
            e.printStackTrace();
        }
        finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while trying to close the file.");
                    e.printStackTrace();
                }
        }


    }
}