package org.algoavengers.weatherwatch.models;

/**
 * LocationData class represents the geographical location data.
 * It includes details like latitude, longitude, city, and country.
 */
public class LocationData {
    public float lat, lon;
    public String city, country;

    /**
     * Default constructor for LocationData class.
     * It initializes the location data with default values.
     */
    public LocationData() {
        lat = lon = 0;
        city = country = "";
    }

    /**
     * Constructor for LocationData class.
     * It initializes the location data using the provided city, country, latitude, and longitude.
     *
     * @param city The city of the location.
     * @param country The country of the location.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     */
    public LocationData(String city, String country, float lat, float lon) {
        this.city = city;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Method to display the location details in the console.
     * It prints the city, country, latitude, and longitude.
     */
    public void displayDetails() {
        System.out.println("City: " + city + ", " + country);
        System.out.println("Latitude: " + lat);
        System.out.println("Longitude: " + lon);
    }

    /**
     * Getter for the latitude of the location.
     *
     * @return The latitude of the location.
     */
    public float getLat() {
        return lat;
    }

    /**
     * Getter for the longitude of the location.
     *
     * @return The longitude of the location.
     */
    public float getLon() {
        return lon;
    }
    // setters
    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setLon(float lon) {
        this.lon = lon;
    }

}