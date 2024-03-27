package org.algoavengers.weatherwatch.storage;

public class FileManagerTester {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        String[] cities = {"Islamabad", "Lahore", "Karachi", "Peshawar", "Quetta"};
        float[] lats = {33.6844f, 31.5497f, 24.8607f, 34.0151f, 30.1798f};
        float[] lons = {73.0479f, 74.3587f, 67.0011f, 71.5805f, 66.9750f};

        // Test the save method with multiple cities
        System.out.println("Testing save method with multiple cities...");
//        for (int i = 0; i < cities.length; i++) {
//            fileManager.save(cities[i], lats[i], lons[i]);
//        }

        // Test the find method with two cities
        System.out.println("Testing find method with two cities...");
        for (int i = 0; i < 2; i++) {
            fileManager.find(cities[i]);
        }

        // Test the delete method with two cities
//        System.out.println("Testing delete method with two cities...");
//        for (int i = 0; i < 2; i++) {
//            fileManager.delete(cities[i]);
//        }

        // Test the find method again with the same two cities
//        System.out.println("Testing find method again with the same two cities...");
//        for (int i = 0; i < 2; i++) {
//            fileManager.find(cities[i]);
//        }

        // Test the delete method again with the same two cities
//        System.out.println("Testing delete method again with the same two cities...");
//        for (int i = 0; i < 2; i++) {
//            fileManager.delete(cities[i]);
//        }
    }
}