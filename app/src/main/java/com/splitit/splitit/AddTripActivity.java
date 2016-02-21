package com.splitit.splitit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

import com.firebase.client.Firebase;

import java.util.ArrayList;
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
        CheckBox friend1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox friend2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox friend3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox friend4 = (CheckBox) findViewById(R.id.checkBox4);
        if (TripActivity.currentUser.getFirstName().equals("Dhruv")) {
            friend1.setText("Ashay Sheth");
            friend2.setText("Crishna Iyengar");
            friend3.setText("Eric Gu");
            friend4.setText("Nikita Bawa");
        } else if (TripActivity.currentUser.getFirstName().equals("Crishna")) {
            friend1.setText("Ashay Sheth");
            friend2.setText("Dhruv Sagar");
            friend3.setText("Eric Gu");
            friend4.setText("Nikita Bawa");
        } else if (TripActivity.currentUser.getFirstName().equals("Ashay")) {
            friend1.setText("Crishna Iyengar");
            friend2.setText("Dhruv Sagar");
            friend3.setText("Eric Gu");
            friend4.setText("Nikita Bawa");
        } else if (TripActivity.currentUser.getFirstName().equals("Nikita")) {
            friend1.setText("Ashay Sheth");
            friend2.setText("Crishna Iyengar");
            friend3.setText("Dhruv Sagar");
            friend4.setText("Eric Wu");
        } else {
            friend1.setText("Ashay Sheth");
            friend2.setText("Crishna Iyengar");
            friend3.setText("Dhruv Sagar");
            friend4.setText("Nikita Bawa");
        }

    }

    public void createTrip(View view) {
        EditText tripName = (EditText) findViewById(R.id.tripName);
        EditText tripPlace = (EditText) findViewById(R.id.tripPlace);
        EditText creator = (EditText) findViewById(R.id.creator);
        CheckBox friend1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox friend2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox friend3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox friend4 = (CheckBox) findViewById(R.id.checkBox4);
        ArrayList<String> userNames = new ArrayList<String>();
        ArrayList<String> userIds = new ArrayList<String>();
        if (friend1.isChecked()) {
            userNames.add(friend1.getText().toString());
        }
        if (friend2.isChecked()) {
            userNames.add(friend2.getText().toString());
        }
        if (friend3.isChecked()) {
            userNames.add(friend3.getText().toString());
        }
        if (friend2.isChecked()) {
            userNames.add(friend3.getText().toString());
        }
        if (userNames.contains("Crishna Iyengar")) {
            userIds.add("facebook:10204578244090971");
        }
        if (userNames.contains("Ashay Sheth")) {
            userIds.add("facebook:10207554191887373");
        }
        userIds.add(TripActivity.currentUser.getId());
        String[] people = {TripActivity.currentUser.getID()};
        Firebase tripsRef = new Firebase("https://split-it.firebaseio.com/").child("trips");
        //Need to add people allowed in trip
        //tripNew.put("person", people);
        Firebase newTripRef = tripsRef.push();
        Trip newTrip = new Trip(tripName.getText().toString(), newTripRef.getKey());
        TripActivity.currentUser.addTrip(newTrip);
        Map<String, String> tripNew = new HashMap<String, String>();
        tripNew.put("name", newTrip.getTripName());
        newTripRef.child("name").setValue(newTrip.getTripName());
        //newTripRef.child("participants").setValue(userIds);
        if (userNames.contains("Ashay Sheth")) {
            Firebase fbRef = new Firebase("https://split-it.firebaseio.com/");
            fbRef.child("users").child("facebook:10207554191887373").child("trips").push().setValue(newTripRef.getKey());
        }
        //newTripRef.setValue(tripNew);
        // Add trip ID to current User t
        Firebase userToAdd = new Firebase("https://split-it.firebaseio.com/").child("users").child(TripActivity.currentUser.getID());
        //String[] allUserTrips = userToAdd.child("trips");
        userToAdd.child("trips").push().setValue(newTripRef.getKey());

        Intent i = new Intent(AddTripActivity.this, TripActivity.class);
        System.out.println("------" + TripActivity.currentUser.getId());
        startActivity(i);
    }
}
