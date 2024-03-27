package org.algoavengers.weatherwatch.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTConverter class provides a utility method to convert Unix timestamp to a formatted date-time string.
 */
public class DTConverter {

    /**
     * Converts a Unix timestamp to a formatted date-time string.
     *
     * @param dt The Unix timestamp to be converted.
     * @return A string representing the date and time in ISO_LOCAL_DATE_TIME format.
     */
    public static String convertToDateTime(long dt) {
        // Create an Instant object from the Unix timestamp
        Instant instant = Instant.ofEpochSecond(dt);

        // Convert the Instant to a ZonedDateTime object using the system's default time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());

        // Format the ZonedDateTime object to a string in ISO_LOCAL_DATE_TIME format and return
        return zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace('T', ' ');
    }
    public static String getDate(String dt) {
        // split dateTime for " " and return the first part
        return dt.split(" ")[0];
    }
    public static String getTime(String dt) {
        // split dateTime for " " and return the second part
        return dt.split(" ")[1];
    }

}