package org.algoavengers.weatherwatch.storage;

import org.algoavengers.weatherwatch.models.*;

import java.sql.SQLException;

public interface CacheManagerInterface {
    public void save(LocationData location, WeatherData weatherData, APData apData) throws SQLException;
    void delete(String city);
    public Object[] find(String city) throws SQLException;
    public LocationData[] getTop5Locations() throws SQLException;
    public void deleteOutdatedRecords() throws SQLException;
}
