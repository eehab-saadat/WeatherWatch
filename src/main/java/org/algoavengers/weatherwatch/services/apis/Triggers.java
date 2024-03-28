package org.algoavengers.weatherwatch.services.apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Triggers {
    private static final String BASE_URL = "http://api.openweathermap.org/data/3.0/triggers";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static long convertToUnix(String timestampStr) {
        LocalDateTime dt = LocalDateTime.parse(timestampStr, formatter);
        return dt.toEpochSecond(ZoneOffset.UTC);
    }

   /* public static String setTrigger(String API_KEY, double lat, double lon) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneWeekLater = currentDateTime.plusWeeks(1);

        String currentDateTimeStr = currentDateTime.format(formatter);
        String oneWeekLaterStr = oneWeekLater.format(formatter);

    }*/
    public static String setTrigger(String API_KEY, String cond, int value, double lat, double lon) throws IOException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneWeekLater = currentDateTime.plusWeeks(1);

        String currentDateTimeStr = currentDateTime.format(formatter);
        String oneWeekLaterStr = oneWeekLater.format(formatter);

        JsonObject data = new JsonObject();
        JsonObject start = new JsonObject();
        start.addProperty("expression", "after");
        start.addProperty("amount", convertToUnix(currentDateTimeStr));
        JsonObject end = new JsonObject();
        end.addProperty("expression", "after");
        end.addProperty("amount", convertToUnix(oneWeekLaterStr));
        JsonObject timePeriod = new JsonObject();
        timePeriod.add("start", start);
        timePeriod.add("end", end);
        data.add("time_period", timePeriod);

        JsonObject condition = new JsonObject();
        condition.addProperty("name", cond);
        condition.addProperty("expression", "$gt");
        condition.addProperty("amount", value);

        JsonArray conditionsArray = new JsonArray();
        conditionsArray.add(condition);

        data.add("conditions", conditionsArray);

        JsonArray coordinatesArray = new JsonArray();
        coordinatesArray.add(lat);
        coordinatesArray.add(lon);

        JsonObject areaObject = new JsonObject();
        areaObject.addProperty("type", "Point");
        areaObject.add("coordinates", coordinatesArray);

        JsonArray areaArray = new JsonArray();
        areaArray.add(areaObject);

        data.add("area", areaArray);

        String jsonData = data.toString();
//        System.out.println(jsonData);
//        return jsonData.toString();
        String url = BASE_URL + "?appid=" + API_KEY;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (var os = connection.getOutputStream()) {
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try (var scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
        }

        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        System.out.println(jsonResponse);
        System.out.println(jsonResponse.get("_id").getAsString());
        return jsonResponse.get("_id").getAsString();
        //return jsonResponse.toString();
    }

    public static String getTrigger(String API_KEY, String id) throws IOException {
        String url = BASE_URL + "/" + id + "?appid=" + API_KEY;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (var scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
        }

        JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
        System.out.println(json);
        return json.toString();
    }
}