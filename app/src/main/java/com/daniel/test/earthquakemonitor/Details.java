package com.daniel.test.earthquakemonitor;

import com.google.android.gms.maps.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Details extends Fragment implements OnMapReadyCallback{

    static Double longitude;
    static Double latitude;
    static Double depth;
    static String magnitude;
    static String dateAndTime;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFrag = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mapFrag == null) {
            mapFrag = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFrag).commit();
        }

        mapFrag.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle info = getArguments();
        populateDetailsView(info.getString("mag"),info.getString("date"),info.getDouble("long"),info.getDouble("lat"),info.getDouble("dep"));

        View detailView = inflater.inflate(R.layout.activity_earthquake_monitor_details, container, false);
        TextView magTx = (TextView)detailView.findViewById(R.id.magnitude);
        TextView dateTx = (TextView)detailView.findViewById(R.id.datetime);
        TextView longTx = (TextView)detailView.findViewById(R.id.longitude);
        TextView latTx = (TextView)detailView.findViewById(R.id.latitude);
        TextView depTx = (TextView)detailView.findViewById(R.id.depth);

        magTx.setText("Magnitude: " + magnitude);
        dateTx.setText("Date: " + dateAndTime);
        longTx.setText("Longitude: " + String.valueOf(longitude));
        latTx.setText("Latitude: " + String.valueOf(latitude));
        depTx.setText("Depth: " + String.valueOf(depth));

        return detailView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng epicenter = new LatLng(latitude,longitude);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(epicenter, 5));
        map.addMarker(new MarkerOptions().title("Epicenter").snippet("Epicenter of earthquake").position(epicenter));
    }

    public static void populateDetailsView(String mag,String datetime, Double new_longitude, Double lat, Double dep){
        longitude = new_longitude;
        latitude = lat;
        depth = dep;
        magnitude = mag;
        dateAndTime = datetime;
    }
}
