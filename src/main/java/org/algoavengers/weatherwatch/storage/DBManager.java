package org.algoavengers.weatherwatch.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBManager implements CacheManagerInterface {
    private static final java.lang.String DB_URL = "jdbc:mysql://sql6.freesqldatabase.com:3306/sql6693696";
    private static final java.lang.String USER = "sql6693696";
    private static final java.lang.String PASSWORD = "RPwZURS5JZ";

    //function to add city to database
    public void save(String city, float lat, float lon) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            //? acts as placeholder
            java.lang.String sql = "INSERT INTO Locations (name, latitude, longitude) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            //setting placeholder text
            statement.setString(1, city);
            statement.setFloat(2, lat);
            statement.setFloat(3, lon);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //delete city from database
    public void delete(String city) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            java.lang.String sql = "DELETE FROM Locations WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, city);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //find city info from database and return it (if not found return null)
    public float[] find(String city) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            java.lang.String sql = "SELECT latitude, longitude FROM Locations WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, city);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                float[] location = new float[2];
                location[0] = rs.getFloat(1); // latitude
                location[1] = rs.getFloat(2); // longitude
                return location;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }



}


