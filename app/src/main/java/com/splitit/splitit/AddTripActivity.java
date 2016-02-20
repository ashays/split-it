package com.splitit.splitit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class AddTripActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
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
        Trip newTrip = new Trip(tripName.getText().toString());
        TripActivity.trips.add(newTrip);
        Firebase firebaseRef = new Firebase("https://split-it.firebaseio.com/");
        Firebase tripsRef = firebaseRef.child("trips");
        Map<String, String> tripNew = new HashMap<String, String>();
        tripNew.put("name", tripName.getText().toString());
        tripsRef.push().setValue(tripNew);
        Intent i = new Intent(AddTripActivity.this, TripActivity.class);
        startActivity(i);
    }
}
