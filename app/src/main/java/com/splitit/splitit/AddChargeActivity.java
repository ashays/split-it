package com.splitit.splitit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class AddChargeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_charge);
    }

    public void actuallyAddCharge(View view) {
        EditText chargeName = (EditText) findViewById(R.id.chargeName);
        EditText chargeAmount = (EditText) findViewById(R.id.chargeAmount);
        System.out.println(chargeName.getText().toString() + " " + chargeAmount.getText().toString());
        System.out.println(TripActivity.currentUser.getID());
//        Firebase firebaseRef = new Firebase("https://split-it.firebaseio.com/");
//        Firebase tripsRef = firebaseRef.child("trips");
//        Map<String, String> tripNew = new HashMap<String, String>();
//        tripNew.put("name", tripName.getText().toString());
//        tripsRef.push().setValue(tripNew);
        Intent i = new Intent(AddChargeActivity.this, OverviewActivity.class);
        startActivity(i);
    }


}
