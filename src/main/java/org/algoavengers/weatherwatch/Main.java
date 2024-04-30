package org.algoavengers.weatherwatch;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.algoavengers.weatherwatch.services.WeatherWatchService;
import org.algoavengers.weatherwatch.storage.CacheManager;
import org.algoavengers.weatherwatch.storage.FileManager;
import org.algoavengers.weatherwatch.storage.DBManager;
import org.algoavengers.weatherwatch.ui.GraphicalUI_App;
import org.algoavengers.weatherwatch.ui.TerminalUI;
import org.algoavengers.weatherwatch.utils.App;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String API_KEY;
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("src/main/resources/API_KEY.json"));
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            API_KEY = jsonObject.get("API_KEY").getAsString();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            return;
        }
        WeatherWatchService.setInstance(new CacheManager(new FileManager()));//set the DB manager choice here
        App WeatherWatch = new App(new GraphicalUI_App());//set the UI choice here
        WeatherWatch.app.run(API_KEY);
    }
}
