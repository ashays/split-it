package com.splitit.splitit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.widget.ListView;
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
    public static String[] listValues;
    public static ArrayAdapter<String> adapter;
    public static Person currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Firebase.setAndroidContext(this);
        final Firebase tripsRef = new Firebase("https://split-it.firebaseio.com/");
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        // Defined Array values to show in ListView
        trips = currentUser.getTrips();
        System.out.println("LOOK HERE" + trips.size());
        listValues = new String[trips.size()];
        int i = 0;
        for (Trip t : trips) {
            listValues[i] = t.getTripName();
            i++;
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

                String currentTripId = currentTrip.getId();
                tripsRef.child("trips").child(currentTripId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean credit = false;
                        OverviewActivity.chargeName.clear();
                        OverviewActivity.chargeValue.clear();

                        
                        for (DataSnapshot charge: snapshot.child("charges").getChildren()) {
                            if (charge.child("transaction").child("bob").exists()) {
                                OverviewActivity.chargeName.add((String) charge.child("name").getValue());
                                OverviewActivity.chargeValue.add((String) charge.child("transaction").child("bob").getValue());
                                HashMap<String, String> transaction = new HashMap<>();
                                for (DataSnapshot information : charge.child("transaction").getChildren()) {
                                    if (!information.getKey().equals("bob"))
                                        transaction.put((String) information.getKey(), (String) information.getValue());
                                }
                                OverviewActivity.everyCharge.add(transaction);
                            }
                        }
                    }
                    /*@Override
                    public void onChildRemoved(DataSnapshot snapshot) {
                        System.out.println("hi");
                    }

                    @Override
                    public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                        System.out.println("Double hi");
                    }

                    @Override
                    public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
                        System.out.println("Leave me alone");
                    }*/

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }
                });

                startActivity(i);
            }
        });
    }

    public void newTrip(View view) {
        Intent i = new Intent(TripActivity.this, AddTripActivity.class);
        startActivity(i);
    }

}