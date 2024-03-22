package org.algoavengers.weatherwatch.storage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.jcp.jdms.framework.ConnectionFactory;
import javax.jcp.jdms.framework.Driver;
import javax.jcp.jdms.framework.ExecutionHandle;
import javax.jcp.jdms.framework.ResultSetCursor;
import javax.jcp.jdms.spi.ResourceId;

public class DBManager implements CacheManagerInterface {
    private static final String DB_URL = "jdbc:mysql://sql6.freesqldatabase.com:3306/sql6693452";
    private static final String USER = "sql6693452";
    private static final String PASSWORD = "wRkCSChMBa";

    public void save(String city, float lat, float lon) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        String sql = "INSERT INTO Locations (name, latitude, longitude) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, city);
        statement.setFloat(2, lat);
        statement.setFloat(3, lon);
        statement.executeUpdate();
        conn.close();
    }

    public void delete(String city) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        String sql = "DELETE FROM Locations WHERE name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, city);
        statement.executeUpdate();
        conn.close();
    }

    public float[] find(String city) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        String sql = "SELECT latitude, longitude FROM Locations WHERE name = ?";
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
    }

}
