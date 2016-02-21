package com.splitit.splitit;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

public class TripActivity extends Activity {
    ListView listView;
    public static Trip currentTrip;
    public static ArrayAdapter<String> adapter;
    public static Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Firebase.setAndroidContext(this);
        if (currentUser.getTrips().size() > 0) {
            refreshTrips();
        } else {
            String[] empty = {"You don't have any trips! Add one to get started!"};
            listView = (ListView) findViewById(R.id.list);
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_2, android.R.id.text1, empty);
            listView.setAdapter(adapter);
        }
    }

    public void newTrip(View view) {
        Intent i = new Intent(TripActivity.this, AddTripActivity.class);
        startActivity(i);
    }

    public void refreshTrips(View view) {
        refreshTrips();
    }

    public void refreshTrips() {
        System.out.println("Refreshing trips");
        final Firebase tripsRef = new Firebase("https://split-it.firebaseio.com/");
        final Firebase tripsRef2 = new Firebase("https://split-it.firebaseio.com/");
        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, TripActivity.currentUser.getTripNames());
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
                System.out.println("Reached here");
                Intent i = new Intent(TripActivity.this, OverviewActivity.class);
                currentTrip = currentUser.getTrips().get(itemPosition);
                String currentTripId = currentTrip.getId();
                tripsRef.child("trips").child(currentTripId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean credit = false;
                        OverviewActivity.chargeName.clear();
                        OverviewActivity.chargeValue.clear();
                        OverviewActivity.peopleAtTrip.clear();

                        for (final DataSnapshot person : snapshot.child("people").getChildren()) {
                            Firebase userRefTemp = new Firebase("https://split-it.firebaseio.com/").child("users").child((String) person.getValue());
                            userRefTemp.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    Person addingPerson = new Person((String) person.getValue());
                                    addingPerson.setName((String) snapshot.child("firstName").getValue(),
                                            (String) snapshot.child("lastName").getValue());
                                    OverviewActivity.peopleAtTrip.add(addingPerson);
                                }

                                @Override
                                public void onCancelled(FirebaseError error) {
                                    //
                                }
                            });
                        }

                        String currentUserId = currentUser.getId();
                        for (DataSnapshot charge : snapshot.child("charges").getChildren()) {
                            if (charge.child("transaction").child(currentUserId).exists()) {
                                OverviewActivity.chargeName.add((String) charge.child("name").getValue());
                                OverviewActivity.chargeValue.add((String) charge.child("transaction").child(currentUserId).getValue());
                                HashMap<String, String> transaction = new HashMap<>();
                                for (DataSnapshot information : charge.child("transaction").getChildren()) {
                                    if (!information.getKey().equals(currentUserId))
                                        transaction.put((String) information.getKey(), (String) information.getValue());
                                }
                                OverviewActivity.everyCharge.add(transaction);
                            }
                        }
                        
                    }
                    @Override
                    public void onCancelled (FirebaseError error) {
                        //go
                    }

                });

//                tripsRef2.child("users").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        for (DataSnapshot person : snapshot.getChildren()) {
//
//                        }
//                    }
//                });

                    startActivity(i);
                }
            });
    }

}