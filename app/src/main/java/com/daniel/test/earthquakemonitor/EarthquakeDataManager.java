package com.daniel.test.earthquakemonitor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EarthquakeDataManager {

    public static class EarthquakeSummary
    {
        String place;
        String magnitude;
        magnitude_color mag_color;

        public EarthquakeSummary(String info_place, String info_magnitude, magnitude_color color){
            place = info_place;
            magnitude =info_magnitude;
            mag_color = color;
        }

        @Override
        public String toString(){
            return place + ": " + magnitude;
        }
    }

    public static class EarthquakeDetails{
        String magnitude;
        String dateAndTime;
        Double depth;
        Double latitude;
        Double longitude;

        public EarthquakeDetails(Double mag,String time, Double longi, Double lat, Double dep){
            magnitude = mag.toString();
            dateAndTime = time;
            depth = dep;
            latitude = lat;
            longitude = longi;
        }

        public String toString(){
            return "magnitude: " + magnitude + " dateAndTime: "+dateAndTime +" depth: " + depth + " lat: " + latitude + " longitude: " + longitude;
        }

    }

    public enum magnitude_color{
        COLOR_LOW,
        COLOR_LOW_MEDIUM,
        COLOR_MEDIUM,
        COLOR_MEDIUM_HIGH,
        COLOR_HIGH,
        COLOR_EXTREME,
        COLOR_NONE
    }


    public static List<EarthquakeSummary> lvInfo = new ArrayList<>();
    public static List<EarthquakeDetails> lvDetails = new ArrayList<>();

    public static void populateInfo(String data){
        //reset list
        if(!lvInfo.isEmpty()) {
            lvInfo.clear();
        }
        if(!lvDetails.isEmpty()){
            lvDetails.clear();
        }
        try{
            JSONObject requestResult = new JSONObject(data);
            JSONArray features = requestResult.getJSONArray("features");
            for(int i = 0; i < features.length(); i++){
                JSONObject field = features.getJSONObject(i);
                JSONObject props = field.getJSONObject("properties");
                String newPlace = props.getString("place");
                Double newMag = props.getDouble("mag");
                EarthquakeSummary newEntry = new EarthquakeSummary(newPlace,newMag.toString(),chooseColor(newMag));
                lvInfo.add(newEntry);
                long datetime = props.getLong("time");
                //get details info
                JSONObject geo = field.getJSONObject("geometry");
                JSONArray coords = geo.getJSONArray("coordinates");
                Double longitude = coords.getDouble(0);
                Double latitude = coords.getDouble(1);
                Double depth = coords.getDouble(2);
                EarthquakeDetails newDetails = new EarthquakeDetails(newMag,EarthquakeDataManager.convertTime(datetime),longitude,latitude,depth);
                lvDetails.add(newDetails);
            }
        }catch(JSONException e){
            Log.e("DBG","JSONException " + e.toString());
            EarthquakeSummary entry = new EarthquakeSummary("No data found","No data found",magnitude_color.COLOR_NONE);
            lvInfo.add(entry);
            e.printStackTrace();
        }
    }

    public static magnitude_color chooseColor(Double magnitude){
        magnitude_color color = magnitude_color.COLOR_NONE;
        if(magnitude >= 0.0 && magnitude <= 0.9){
            color = magnitude_color.COLOR_NONE;
        }
        if(magnitude >= 1.0 && magnitude <= 1.9){
           color = magnitude_color.COLOR_LOW_MEDIUM;
        }
        if(magnitude >= 2.0 && magnitude <= 3.9){
            color = magnitude_color.COLOR_MEDIUM;
        }
        if(magnitude >= 4.0 && magnitude <= 5.9){
            color = magnitude_color.COLOR_MEDIUM_HIGH;
        }
        if(magnitude >= 6.0 && magnitude <= 8.9){
            color = magnitude_color.COLOR_HIGH;
        }
        if(magnitude >= 9.0){
            color = magnitude_color.COLOR_EXTREME;
        }

        return color;
    }

    public static String convertTime(long time){
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("EEE yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return formatter.format(date);

    }

}
