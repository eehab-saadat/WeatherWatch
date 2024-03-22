package org.algoavengers.weatherwatch.storage;

public class DBManager implements CacheManagerInterface {
    public void save(String city, float lat, float lon) {
        // Save to DB
    }
    public void delete(String city) {
        // Delete from DB
    }

    public float[] find(String city) {
        // Find from DB
        return new float[2];
    }
}
