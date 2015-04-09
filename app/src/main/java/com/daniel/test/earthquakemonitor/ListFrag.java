package com.daniel.test.earthquakemonitor;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Daniel on 4/9/2015.
 */
public class ListFrag extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_earthquake_monitor_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(!EarthquakeDataManager.lvDetails.isEmpty()) {
            EarthquakeMonitor.displayDetails(position);
        }
    }
}
