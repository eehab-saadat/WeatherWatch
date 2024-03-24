package org.algoavengers.weatherwatch.storage;

public interface CacheManagerInterface {

    void save(String city, float lat, float lon);
    void delete(String city);
    float[] find(String city);

}