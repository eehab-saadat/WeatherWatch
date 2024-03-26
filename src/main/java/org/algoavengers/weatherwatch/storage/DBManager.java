package org.algoavengers.weatherwatch.storage;

import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class DBManager implements CacheManagerInterface {
    private static final java.lang.String DB_URL = "jdbc:mysql://sql6.freesqldatabase.com:3306/sql6693906";
    private static final java.lang.String USER = "sql6693906";
    private static final java.lang.String PASSWORD = "VDA1QRU1Re";

    //function to add city to database
    public void save(LocationData location, WeatherData weatherData, APData apData) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Check for duplicate location before insert
            String checkLocationSql = "SELECT id FROM Locations WHERE name = ?";
            PreparedStatement checkLocationStatement = conn.prepareStatement(checkLocationSql);
            checkLocationStatement.setString(1, location.city);
            ResultSet rs = checkLocationStatement.executeQuery();

            int locationId;
            if (rs.next()) {
                locationId = rs.getInt(1);
            } else {
                // Insert location if not found
                String insertLocationSql = "INSERT INTO Locations (name, latitude, longitude, country) VALUES (?, ?, ?, ?)";
                PreparedStatement insertLocationStatement = conn.prepareStatement(insertLocationSql);
                insertLocationStatement.setString(1, location.city);
                insertLocationStatement.setFloat(2, location.lat);
                insertLocationStatement.setFloat(3, location.lon);
                insertLocationStatement.setString(4, location.country);
                insertLocationStatement.executeUpdate();

                // Get the newly inserted location ID
                String getLastIdSql = "SELECT LAST_INSERT_ID()";
                Statement getLastIdStatement = conn.createStatement();
                rs = ((Statement) getLastIdStatement).executeQuery(getLastIdSql);
                rs.next();
                locationId = rs.getInt(1);


                // Insert weather data
                String insertWeatherSql = "INSERT INTO WeatherDetails (location_id, temp, feelsLike, tempMin, tempMax, pressure, humidity, windSpeed, visibility, main, description, icon, sunrise, sunset, dt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertWeatherStatement = conn.prepareStatement(insertWeatherSql);
                insertWeatherStatement.setInt(1, locationId);
                insertWeatherStatement.setFloat(2, weatherData.temp);
                insertWeatherStatement.setFloat(3, weatherData.feelsLike);
                insertWeatherStatement.setFloat(4, weatherData.tempMin);
                insertWeatherStatement.setFloat(5, weatherData.tempMax);
                insertWeatherStatement.setFloat(6, weatherData.pressure);
                insertWeatherStatement.setFloat(7, weatherData.humidity);
                insertWeatherStatement.setFloat(8, weatherData.windSpeed);
                insertWeatherStatement.setFloat(9, weatherData.visibility);
                insertWeatherStatement.setString(10, weatherData.main);
                insertWeatherStatement.setString(11, weatherData.description);
                insertWeatherStatement.setString(12, weatherData.icon);
                insertWeatherStatement.setString(13, weatherData.sunrise);
                insertWeatherStatement.setString(14, weatherData.sunset);
                insertWeatherStatement.setString(15, weatherData.dt);
                insertWeatherStatement.executeUpdate();

                // Insert air pollution data
                String insertAPSql = "INSERT INTO APDetails (location_id, aqi, co, no, no2, o3, so2, pm2_5, pm10, nh3, comment, dt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertAPStatement = conn.prepareStatement(insertAPSql);
                insertAPStatement.setInt(1, locationId);
                insertAPStatement.setInt(2, apData.aqi);
                insertAPStatement.setFloat(3, apData.co);
                insertAPStatement.setFloat(4, apData.no);
                insertAPStatement.setFloat(5, apData.no2);
                insertAPStatement.setFloat(6, apData.o3);
                insertAPStatement.setFloat(7, apData.so2);
                insertAPStatement.setFloat(8, apData.pm2_5);
                insertAPStatement.setFloat(9, apData.pm10);
                insertAPStatement.setFloat(10, apData.nh3);
                insertAPStatement.setString(11, apData.comment);
                insertAPStatement.setString(12, apData.dt);
                insertAPStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(String city) {

    }

    //find city from database
    public Object[] find(String city) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String sql = "SELECT l.*, wd.*, ap.* " +
                    "FROM Locations l " +
                    "INNER JOIN WeatherDetails wd ON l.id = wd.location_id " +
                    "INNER JOIN APDetails ap ON l.id = ap.location_id " +
                    "WHERE LOWER(l.name) = ?";  // Case-insensitive search

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, city.toLowerCase()); // Convert city to lowercase for comparison
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                LocationData locationData = new LocationData();
                locationData.city = rs.getString("name");
                locationData.lat = rs.getFloat("latitude");
                locationData.lon = rs.getFloat("longitude");
                locationData.country = rs.getString("country");

                WeatherData weatherData = new WeatherData();
                weatherData.temp = rs.getFloat("temp");
                weatherData.feelsLike = rs.getFloat("feelsLike");
                weatherData.tempMin = rs.getFloat("tempMin");
                weatherData.tempMax = rs.getFloat("tempMax");
                weatherData.pressure = rs.getFloat("pressure");
                weatherData.humidity = rs.getFloat("humidity");
                weatherData.windSpeed = rs.getFloat("windSpeed");
                weatherData.visibility = rs.getFloat("visibility");
                weatherData.main = rs.getString("main");
                weatherData.description = rs.getString("description");
                weatherData.icon = rs.getString("icon");
                weatherData.sunrise = rs.getString("sunrise");
                weatherData.sunset = rs.getString("sunset");
                weatherData.dt = rs.getString("dt");

                APData apData = new APData();
                apData.aqi = rs.getInt("aqi");
                apData.co = rs.getFloat("co");
                apData.no = rs.getFloat("no");
                apData.no2 = rs.getFloat("no2");
                apData.o3 = rs.getFloat("o3");
                apData.so2 = rs.getFloat("so2");
                apData.pm2_5 = rs.getFloat("pm2_5");
                apData.pm10 = rs.getFloat("pm10");
                apData.nh3 = rs.getFloat("nh3");
                apData.comment = rs.getString("comment");
                apData.dt = rs.getString("dt");

                return new Object[]{locationData, weatherData, apData};
            } else {
                return null;
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }


    //find top 5 city data
    public LocationData[] getTop5Locations() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String sql = "SELECT l.* " +
                    "FROM Locations l " +
                    "INNER JOIN WeatherDetails wd ON l.id = wd.location_id " +
                    "ORDER BY wd.dt DESC " +  // Order by latest weather data timestamp (dt)
                    "LIMIT 5";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            List<LocationData> locations = new ArrayList<>();
            while (rs.next() && locations.size() < 5) {
                LocationData locationData = new LocationData();
                locationData.city = (rs.getString("name")); // Use setters or public member variables
                locationData.lat = (rs.getFloat("latitude"));
                locationData.lon = (rs.getFloat("longitude"));
                locationData.country = (rs.getString("country"));
                locations.add(locationData);
            }

            // Convert List to array (optional, can return the List directly)
            return locations.toArray(new LocationData[locations.size()]);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }





    //delete outdated data from database
    public void deleteOutdatedRecords(){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String currentDate = getCurrentDate();  // Get current date minus 1 day

            String sql = "DELETE FROM Locations  " +
                    "WHERE Locations.id in ( " +
                    "   SELECT wd.location_id " +
                    "   FROM WeatherDetails wd " +
                    "   WHERE wd.location_id = Locations.id AND wd.dt < ? )";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, currentDate);
            statement.executeUpdate();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        
    }


    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Adjust format
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);  // Subtract 1 day from current date
        return sdf.format(cal.getTime());

    }



}
