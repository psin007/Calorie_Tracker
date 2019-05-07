package com.example.mycalorietracker;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {
    private static final String BASE_URL = "http://118.139.68.100:8080/CalorieTracker/webresources/";

    public static Boolean checkusercre(String username,String passwordhash) {
        final String methodPath = "restcalorietracker.credential/checkCredentials/" + username +"/"+passwordhash;
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
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
//            Log.e("inStream", inStream.nextLine().toString());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
//                Log.e("textresult",textResult);
            }
//            Log.e("inStream",inStream.nextLine());
           // textResult = inStream.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        Log.e("textresult",textResult);
        return Boolean.valueOf(textResult);

    }
}