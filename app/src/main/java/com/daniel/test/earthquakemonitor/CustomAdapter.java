package com.daniel.test.earthquakemonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daniel on 4/9/2015.
 */
public class CustomAdapter extends ArrayAdapter<EarthquakeDataManager.EarthquakeSummary> {
    private final Context context;
    private final List<EarthquakeDataManager.EarthquakeSummary> values;

    public CustomAdapter(Context context, List<EarthquakeDataManager.EarthquakeSummary> values ){
        super(context,R.layout.rows_layout,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rows_layout,parent,false);
        TextView placeText = (TextView)rowView.findViewById(R.id.place_text);
        TextView magnitudeText = (TextView)rowView.findViewById(R.id.magnitude_text_1);
        TextView magnitudeValueText = (TextView)rowView.findViewById(R.id.magnitude_text_2);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.more_info);

        placeText.setText("Place: " + values.get(position).place);
        magnitudeText.setText("Magnitude: ");
        magnitudeValueText.setText(values.get(position).magnitude);

        if(values.get(position).mag_color == EarthquakeDataManager.magnitude_color.COLOR_LOW){
            magnitudeValueText.setTextColor(context.getResources().getColor(R.color.low));
        }
        if(values.get(position).mag_color == EarthquakeDataManager.magnitude_color.COLOR_LOW_MEDIUM){
            magnitudeValueText.setTextColor(context.getResources().getColor(R.color.low_medium));
        }
        if(values.get(position).mag_color == EarthquakeDataManager.magnitude_color.COLOR_MEDIUM){
            magnitudeValueText.setTextColor(context.getResources().getColor(R.color.medium));
        }
        if(values.get(position).mag_color == EarthquakeDataManager.magnitude_color.COLOR_MEDIUM_HIGH){
            magnitudeValueText.setTextColor(context.getResources().getColor(R.color.medium_high));
        }
        if(values.get(position).mag_color == EarthquakeDataManager.magnitude_color.COLOR_HIGH){
            magnitudeValueText.setTextColor(context.getResources().getColor(R.color.high));
        }
        if(values.get(position).mag_color == EarthquakeDataManager.magnitude_color.COLOR_EXTREME){
            magnitudeValueText.setTextColor(context.getResources().getColor(R.color.extreme));
        }

        imageView.setImageResource(R.drawable.ic_more_info);

        return rowView;

    }


}
