package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    /** URL for earthquake data from the USGS dataset */
    /** JSON response for a USGS query */
    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    /** Adapter for the list of earthquakes */
    private QuakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        new EarthquakeAsyncTask().execute(USGS_REQUEST_URL);

        // Create a empty list of earthquake info.
        ArrayList<quakeInfo> earthquakesArrayList = new ArrayList<quakeInfo>();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new QuakeAdapter(this, earthquakesArrayList);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Set the OnItemClickListener on the adapter view, so when a user clicks on an earthquake view
        //they are redirected to the USGS website with more info on that particular earthquake
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                quakeInfo currentEarthquake = (quakeInfo) mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }
    private class EarthquakeAsyncTask extends AsyncTask<String, Void,List<quakeInfo>> {
        @Override
        protected List<quakeInfo> doInBackground(String... url){

            // Don't perform the request if there are no URLs, or the first URL is null.
            if (url.length < 1 || url[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response if url exists.
            List<quakeInfo> earthquake = QueryUtils.fetchEarthquakeData(url[0]);
            return earthquake;
        }
        protected void onPostExecute(List<quakeInfo> earthquakeResult){
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (earthquakeResult != null && !earthquakeResult.isEmpty()) {
                mAdapter.addAll(earthquakeResult);
            }
        }
    }
}
