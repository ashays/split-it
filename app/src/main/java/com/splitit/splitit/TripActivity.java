package com.splitit.splitit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import android.widget.Toast;
import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

public class TripActivity extends Activity {
    public static ArrayList<Trip> trips = new ArrayList<Trip>();
    ListView listView;
    public static Trip currentTrip;
    public static ArrayList<String> listValues;
    public static ArrayAdapter<String> adapter;
    public static Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Firebase.setAndroidContext(this);
        Firebase firebaseRef = new Firebase("https://split-it.firebaseio.com/");
        Firebase tripsRef = firebaseRef.child("trips");
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        System.out.println("Got to Trip!!");
        // Defined Array values to show in ListView
        trips = currentUser.getTrips();
        listValues = new ArrayList<String>();
        int i = 0;
        for (Trip t : trips) {
            System.out.println(t.getTripName());
            String lebron = t.getTripName();
            System.out.println(listValues);
            listValues.add(lebron);
        }
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, listValues);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                // Show Alert
                Intent i = new Intent(TripActivity.this, OverviewActivity.class);
                currentTrip = trips.get(itemPosition);
                startActivity(i);
            }
        });
    }

    public void newTrip(View view) {
        Intent i = new Intent(TripActivity.this, AddTripActivity.class);
        startActivity(i);
    }

}