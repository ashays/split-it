package com.splitit.splitit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.facebook.FacebookSdk;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Firebase firebaseRef = new Firebase("https://split-it.firebaseio.com/");
        final Firebase tripsRef = firebaseRef.child("trips");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                TripActivity.currentUser = new Person("Crishna", "Iyengar", "903");
                tripsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        TripActivity.currentUser.getTrips().clear();
                        System.out.println("There are " + snapshot.getChildrenCount() + " trips");
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            System.out.println("hihi");
                            String tripName = (String) postSnapshot.child("name").getValue();
                            Trip newTrip = new Trip(tripName);
                            newTrip.setId(postSnapshot.getKey());
                            TripActivity.currentUser.addTrip(newTrip);
                        }
                        Intent i = new Intent(LoginActivity.this, TripActivity.class);
                        startActivity(i);
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }
        });
    }

}
