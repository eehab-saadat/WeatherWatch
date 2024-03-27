package org.algoavengers.weatherwatch.storage;

import org.algoavengers.weatherwatch.models.APData;
import org.algoavengers.weatherwatch.models.LocationData;
import org.algoavengers.weatherwatch.models.WeatherData;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;


public class DBManager implements CacheManagerInterface {
    private static final java.lang.String DB_URL = "jdbc:mysql://sql6.freesqldatabase.com:3306/sql6693906";
    private static final java.lang.String USER = "sql6693906";
    private static final java.lang.String PASSWORD = "VDA1QRU1Re";

    //function to add city to database

    public void save(LocationData location, WeatherData weatherData, APData apData, WeatherData[] weatherForecasts) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Check for duplicate location before insert (optional for efficiency)
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

                // Insert weather forecasts (one record per forecast day)
                for (WeatherData forecast : weatherForecasts) {
                    String insertForecastSql = "INSERT INTO WeatherForecasts (location_id, temp, feelsLike, tempMin, tempMax, pressure, humidity, windSpeed, visibility, main, description, icon, dt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertForecastStatement = conn.prepareStatement(insertForecastSql);
                    insertForecastStatement.setInt(1, locationId);
                    insertForecastStatement.setFloat(2, forecast.temp);
                    insertForecastStatement.setFloat(3, forecast.feelsLike);
                    insertForecastStatement.setFloat(4, forecast.tempMin);
                    insertForecastStatement.setFloat(5, forecast.tempMax);
                    insertForecastStatement.setFloat(6, forecast.pressure);
                    insertForecastStatement.setFloat(7, forecast.humidity);
                    insertForecastStatement.setFloat(8, forecast.windSpeed);
                    insertForecastStatement.setFloat(9, forecast.visibility);
                    insertForecastStatement.setString(10, forecast.main);
                    insertForecastStatement.setString(11, forecast.description);
                    insertForecastStatement.setString(12, forecast.icon);
                    insertForecastStatement.setString(13, forecast.dt);
                    insertForecastStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Log or handle the exception as needed
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(LocationData location, WeatherData weatherData, APData apData) {

    }


    @Override
    //code to delete location from database based on city name
    public void delete(String city) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            java.lang.String sql = "DELETE FROM Locations WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, city);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //find city from database
    @Override

    public Object[] find(String city){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Find location details based on city name
            String findLocationSql = "SELECT * FROM Locations WHERE name = ?";
            PreparedStatement locationStatement = conn.prepareStatement(findLocationSql);
            locationStatement.setString(1, city);
            ResultSet rs = locationStatement.executeQuery();

            if (!rs.next()) {
                return null;  // No location found
            }

            LocationData location = new LocationData();
            location.city=(rs.getString("name"));
            location.lat=(rs.getFloat("latitude"));
            location.lon=(rs.getFloat("longitude"));
            location.country=(rs.getString("country"));
            int locationId = rs.getInt("id");

            // Find weather data for the location
            String findWeatherSql = "SELECT * FROM WeatherDetails WHERE location_id = ?";
            PreparedStatement weatherStatement = conn.prepareStatement(findWeatherSql);
            weatherStatement.setInt(1, locationId);
            rs = weatherStatement.executeQuery();

            WeatherData weatherData = null;
            if (rs.next()) {
                weatherData = new WeatherData();
                weatherData.temp=(rs.getFloat("temp"));
                weatherData.feelsLike=(rs.getFloat("feelsLike"));
                weatherData.tempMin=(rs.getFloat("tempMin"));
                weatherData.tempMax=(rs.getFloat("tempMax"));
                weatherData.pressure=(rs.getFloat("pressure"));
                weatherData.humidity=(rs.getFloat("humidity"));
                weatherData.windSpeed=(rs.getFloat("windSpeed"));
                weatherData.visibility=(rs.getFloat("visibility"));
                weatherData.main=(rs.getString("main"));
                weatherData.description=(rs.getString("description"));
                weatherData.icon=(rs.getString("icon"));
                weatherData.sunrise=(rs.getString("sunrise"));
                weatherData.sunset=(rs.getString("sunset"));
                weatherData.dt=(rs.getString("dt"));

            }

            // Find air pollution data for the location
            String findAPSql = "SELECT * FROM APDetails WHERE location_id = ?";
            PreparedStatement apStatement = conn.prepareStatement(findAPSql);
            apStatement.setInt(1, locationId);
            rs = apStatement.executeQuery();

            APData apData = null;
            if (rs.next()) {
                apData = new APData();
                apData.aqi=(rs.getInt("aqi"));
                apData.co=(rs.getFloat("co"));
                apData.no=(rs.getFloat("no"));
                apData.no2=(rs.getFloat("no2"));
                apData.o3=(rs.getFloat("o3"));
                apData.so2=(rs.getFloat("so2"));
                apData.pm2_5=(rs.getFloat("pm2_5"));
                apData.pm10=(rs.getFloat("pm10"));
                apData.nh3=(rs.getFloat("nh3"));
                apData.comment=(rs.getString("comment"));
                apData.dt=(rs.getString("dt"));

            }

            // Find weather forecasts for the location (maximum 5 days)
            WeatherData[] weatherForecasts = new WeatherData[5];
            String findForecastSql = "SELECT * FROM WeatherForecasts WHERE location_id = ? ORDER BY dt LIMIT 5";
            PreparedStatement forecastStatement = conn.prepareStatement(findForecastSql);
            forecastStatement.setInt(1, locationId);
            rs = forecastStatement.executeQuery();

            int i = 0;
            while (rs.next() && i < 5) {
                WeatherData forecast = new WeatherData();
                forecast.temp=(rs.getFloat("temp"));
                forecast.feelsLike=(rs.getFloat("feelsLike"));
                forecast.tempMin=(rs.getFloat("tempMin"));
                forecast.tempMax=(rs.getFloat("tempMax"));
                forecast.pressure=(rs.getFloat("pressure"));
                forecast.humidity=(rs.getFloat("humidity"));
                forecast.windSpeed=(rs.getFloat("windSpeed"));
                forecast.visibility=(rs.getFloat("visibility"));
                forecast.main=(rs.getString("main"));
                forecast.description=(rs.getString("description"));
                forecast.icon=(rs.getString("icon"));
                forecast.dt=(rs.getString("dt"));
                weatherForecasts[i++] = forecast;
            }

            return new Object[]{location, weatherData, apData, weatherForecasts};

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //find top 5 city data
    public LocationData[] getTop5Locations() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            java.lang.String sql = "SELECT l.* " +
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

            java.lang.String sql = "DELETE FROM Locations  " +
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

    public LocationData[] getSavedLocations(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String sql = "SELECT * FROM Locations WHERE IsSaved = 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            List<LocationData> locations = new ArrayList<>();
            while (rs.next()) {
                LocationData location = new LocationData();
                location.city=(rs.getString("name"));
                location.lat=(rs.getFloat("latitude"));
                location.lon=(rs.getFloat("longitude"));
                location.country=(rs.getString("country"));
                locations.add(location);
            }

            // Convert List to array (optional)
            return locations.toArray(new LocationData[locations.size()]);
        } catch (SQLException e) {
            e.printStackTrace();  // Log or handle the exception as needed
            return null;  // Return null on error
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveLocation(LocationData location){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Check the total number of saved locations
            String countSavedLocationsSql = "SELECT COUNT(*) FROM Locations WHERE IsSaved = 1";
            PreparedStatement countStatement = conn.prepareStatement(countSavedLocationsSql);
            ResultSet countRs = countStatement.executeQuery();
            countRs.next();
            int savedLocationsCount = countRs.getInt(1);

            // Restrict saving if already at max limit
            if (savedLocationsCount >= 5) {
                System.err.println("Error: Maximum of 5 locations can be saved!");
                return;
            }

            // Update IsSaved bit for the specified location if saves available
            String updateIsSavedSql = "UPDATE Locations SET IsSaved = 1 WHERE name = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateIsSavedSql);
            updateStatement.setString(1, location.city);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeLocation(String city) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Find location ID based on city name
            String getLocationIdSql = "SELECT id FROM Locations WHERE name = ?";
            PreparedStatement locationStatement = conn.prepareStatement(getLocationIdSql);
            locationStatement.setString(1, city);
            ResultSet rs = locationStatement.executeQuery();
            if (!rs.next()) {
                System.err.println("Error: Location not found!");
                return;
            }

            int locationId = rs.getInt(1);

            // Update IsSaved bit to false for the location
            String updateIsSavedSql = "UPDATE Locations SET IsSaved = 0 WHERE id = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateIsSavedSql);
            updateStatement.setInt(1, locationId);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
