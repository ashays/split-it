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

    }

    public void createTrip(View view) {
        EditText tripName = (EditText) findViewById(R.id.tripName);
        EditText tripPlace = (EditText) findViewById(R.id.tripPlace);
        EditText creator = (EditText) findViewById(R.id.creator);
        String[] people = {TripActivity.currentUser.getID()};
        Trip newTrip = new Trip(tripName.getText().toString());
        TripActivity.currentUser.addTrip(newTrip);
        Firebase tripsRef = new Firebase("https://split-it.firebaseio.com/").child("trips");
        Map<String, String> tripNew = new HashMap<String, String>();
        tripNew.put("name", tripName.getText().toString());
        //Need to add people allowed in trip
        //tripNew.put("person", people);
        Firebase newTripRef = tripsRef.push();
        newTripRef.setValue(tripNew);
        // Add trip ID to current User t
        Firebase userToAdd = new Firebase("https://split-it.firebaseio.com/").child("users").child(TripActivity.currentUser.getID());
        //String[] allUserTrips = userToAdd.child("trips");
        userToAdd.child("trips").push().setValue(newTripRef.getKey());

        Intent i = new Intent(AddTripActivity.this, TripActivity.class);
        startActivity(i);
    }
}
