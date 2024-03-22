package org.algoavengers.weatherwatch.storage;

import java.io.*;
public class FileManager implements CacheManagerInterface {
    public void save(String city, float lat, float lon) {
        try {
            FileWriter writer = new FileWriter("storage.txt", true);
            BufferedWriter bw = new BufferedWriter(writer);
            String coma = ", ";
            bw.write(city);
            bw.write(coma);
            bw.write(String.valueOf(lat));
            bw.write(coma);
            bw.write(String.valueOf(lon));
            bw.newLine();
            bw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public void delete(String city) {


        // Delete from DB
    }

    public float[] find(String city) {
        // Find from DB
        return new float[2];
    }
}


class main
{
    public static void main(String[] args)
    {
        FileManager obj= new FileManager();
        String city="Lahore";
        float lat=31;
        float lon=74;


        obj.save(city, lat,lon);
    }
}