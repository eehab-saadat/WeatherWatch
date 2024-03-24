package org.algoavengers.weatherwatch.services;

//imports
import com.google.gson.Gson;

import java.util.HashMap;
public interface WeatherWatchServiceInterface {

    // adds a location to the saved locations table/list in database
    void addLocation(double latitude, double longitude); // usecase#1
    // should decode the coordinates to a location and save both coordinates and the location to the database
    // if it is a valid location otherwise return an error message
    void addLocation(String name); // usecase # 2
    // should encode the location to coordinates (latitutde,longitude) and save
    // both coordinates and the location to the database if it is a valid location otherwise
    // return an error message (must accommodate for both country and city locations)
    HashMap getCurrentWeather(double latitude, double longitude); // usecase #3,4,5; see: https://openweathermap.org/current
    // should use the current weather API to get current weather + basic information + sunrise,sunset
    // first through database, then if not found, through the API
    // for nested hashmaps; check source : https://medium.com/@mmmohamedsameem/dynamic-nested-json-to-hashmap-of-key-value-pair-1832e57b525
    //                                     https://www.baeldung.com/gson-json-to-map //using gson
    HashMap getBasicInfo(HashMap data); // usecase # 4
    // should use the hashmap returned by getCurrentWeather() to get basic information about the location
    HashMap getSunriseSunset(HashMap data); // usecase # 5
    // should use the hashmap returned by getCurrentWeather() to get sunrise and sunset times
    HashMap get5dayWeatherForecast(double latitude, double longitude); // usecase # 6; see: https://openweathermap.org/forecast5
    HashMap getAirPollutionInfo(double latitude, double longitude); // usecase # 10; see: https://openweathermap.org/api/air-pollution
    int getAQI(double latitude, double longitude);// usecase get# 10; should use the hashmap returned by getAirPollutionInfo() to get the AQI
    HashMap getPollutingGasInfo(HashMap airPollutionData); // usecase # 12; should use the hashmap returned by getAirPollutionInfo() to get the polluting gas information
    void setTrigger(Gson params); // usecase # 9,11; should set a trigger for a location to notify the user when the weather changes
    void setAQITrigger(int targetAQI);
    // should settTrigger for a location to notify the user when the AQI changes

}
