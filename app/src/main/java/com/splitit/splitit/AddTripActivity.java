package com.splitit.splitit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

public class AddTripActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        EditText tripName = (EditText) findViewById(R.id.tripName);
        EditText tripPlace = (EditText) findViewById(R.id.tripPlace);
        EditText creator = (EditText) findViewById(R.id.creator);
        tripName.setText("Trip Name");
        tripPlace.setText("Trip Place");
        creator.setText("Creator");

    }

    public void createTrip(View view) {
        EditText tripName = (EditText) findViewById(R.id.tripName);
        EditText tripPlace = (EditText) findViewById(R.id.tripPlace);
        EditText creator = (EditText) findViewById(R.id.creator);
        Trip newTrip = new Trip();
        newTrip.setTripName(tripName.getText().toString());
        TripActivity.trips.add(newTrip);
        Intent i = new Intent(AddTripActivity.this, TripActivity.class);
        startActivity(i);
    }
}
