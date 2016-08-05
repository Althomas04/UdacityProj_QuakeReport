package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by al.thomas04 on 5/30/2016.
 */
public class QuakeAdapter extends ArrayAdapter {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context  The current context. Used to inflate the layout file.
     * @param quakeInfoList A List of earthquake info objects to display in a list
     */
    public QuakeAdapter(Activity context, ArrayList<quakeInfo> quakeInfoList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, quakeInfoList);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being resused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link word} object located at this position in the list
        quakeInfo currentQuakeInfo = (quakeInfo) getItem(position);

        // Find the TextView in the list_item.xml layout with the ID mag_text_view
        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        // Format the magnitude string (i.e. "0.0")
        String formattedMagnitude = formatMagnitude(currentQuakeInfo.getMagnitude());
        // Set the formatted magnitude string text on the magTextView
        magTextView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentQuakeInfo.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //Get the location info from the current quakeInfo object
        String quakeLocation = currentQuakeInfo.getLocation();
        String locationOffset;
        String primaryLocation;

        //Get the locationOffset and the primaryLocation by splitting the location object
        if (quakeLocation.contains("of")) {
            String[] parts = quakeLocation.split("(?<=of)");
            locationOffset = parts[0];
            primaryLocation = parts[1];

        } else {
            locationOffset = "Near the";
            primaryLocation = quakeLocation;
        }

        // Find the TextView in the list_item.xml layout with the ID location_offset_text_view
        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.location_offset_text_view);
        // Get the locationOffset from the current location object and set this text on the location_offset_text_view
        locationOffsetTextView.setText(locationOffset);

        // Find the TextView in the list_item.xml layout with the ID primary_location_text_view
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location_text_view);
        // Get the primaryLocation from the current location object and set this text on the primary_location_text_view
        primaryLocationTextView.setText(primaryLocation);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentQuakeInfo.getTimeInMillisec());

        // Find the TextView in the list_item.xml layout with the ID date_text_view
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Set the formatted date string text on the dateTextView
        dateTextView.setText(formattedDate);

        // Find the TextView in the list_item.xml layout with the ID date_text_view
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Set the formatted time string  text on the dateTextView
        timeTextView.setText(formattedTime);

        // Return the whole list item layout so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the formatted magnitude string (i.e. "0.0") from the magnitude.
     */
    private String formatMagnitude(Double magObject) {
        DecimalFormat magnitudeFormatter = new DecimalFormat("0.0");
        return magnitudeFormatter.format(magObject);
    }

     private int getMagnitudeColor(Double magObject){
        int magnitudeColorResId;
        int magFloor = (int) Math.floor(magObject);
        switch (magFloor){
            case 0:
            case 1:
                magnitudeColorResId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResId);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormatter.format(dateObject);
    }

    /**
     * Return the formatted time string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        return timeFormatter.format(dateObject);
    }

}


