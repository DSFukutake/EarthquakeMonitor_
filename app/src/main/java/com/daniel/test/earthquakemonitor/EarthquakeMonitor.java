package com.daniel.test.earthquakemonitor;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class EarthquakeMonitor extends ActionBarActivity {

    public static String geoJson = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    public static String currentInfo = "";
    private ListFragment lFrag;
    private static FragmentManager fragMangr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lFrag = new ListFrag();
        fragMangr = getSupportFragmentManager();
        setContentView(R.layout.activity_earthquake_monitor);
    }

    @Override
    protected void onStart(){
       super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        RequestTask requestTask = new RequestTask(this);
        requestTask.execute(geoJson);
        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_earthquake_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_refresh) {
            //Call the method that retrieves the json file with the earthquake information.
           RequestTask requestTask = new RequestTask(this);
           requestTask.execute(geoJson);
           populateListView();
        }
        if(id == R.id.action_back){
            populateListView();
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateListView(){
            lFrag.setListAdapter(new CustomAdapter(this,EarthquakeDataManager.lvInfo));
            FragmentTransaction fragmentTransaction = fragMangr.beginTransaction();
            fragmentTransaction.replace(android.R.id.content,lFrag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

    }

    public static void displayDetails(int index){

        Double longit = EarthquakeDataManager.lvDetails.get(index).longitude;
        Double latd = EarthquakeDataManager.lvDetails.get(index).latitude;
        Double dep = EarthquakeDataManager.lvDetails.get(index).depth;
        String mag =  EarthquakeDataManager.lvDetails.get(index).magnitude;
        String date = EarthquakeDataManager.lvDetails.get(index).dateAndTime;

        Bundle detailInfo = new Bundle();
        detailInfo.putString("mag",mag);
        detailInfo.putString("date",date);
        detailInfo.putDouble("long",longit);
        detailInfo.putDouble("lat",latd);
        detailInfo.putDouble("dep",dep);


        android.support.v4.app.Fragment fr = new Details();
        fr.setArguments(detailInfo);
        FragmentTransaction fragmentTransaction = fragMangr.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fr);
        fragmentTransaction.commit();


    }

}
