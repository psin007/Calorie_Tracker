package com.example.mycalorietracker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PlacesGoogle {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    //method to form url and get result for park within 5km
    public static String getPlaces(String latitude,String longitude,String PROXIMITY_RADIUS,String nearbyPlace) {
        final String methodPath = "location="+latitude+","+longitude+"&radius="+PROXIMITY_RADIUS+"&type="+nearbyPlace+"&key="+"AIzaSyDzVi7Qme8Mpbwyn7qSVq7jrUBi8G-ryAM";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
    public  static Park[] getParks(String result){
        double lat = 0;
        double lon=0;
        String name="";
        Park[] parks=null;

        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonlinkArray = jsonObject.getJSONArray("results");
            parks = new Park[jsonlinkArray.length()];


            for(int i = 0; i<jsonlinkArray.length();i++){
                lat = jsonlinkArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"); //from json response
                lon = jsonlinkArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                name = jsonlinkArray.getJSONObject(i).getString("name");
                parks[i]=new Park(lat,lon,name);
            }
        }
        catch(Exception e){
                      e.printStackTrace();

        }

        return parks;

    }
}
