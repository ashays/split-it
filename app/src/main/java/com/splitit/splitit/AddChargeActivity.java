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
        Firebase chargesRef = new Firebase("https://split-it.firebaseio.com/").child("trips").child(TripActivity.currentTrip.getId()).child("charges");
        Firebase newChargeRef = chargesRef.push();
        Map<String, String> newCharge = new HashMap<String, String>();
        newCharge.put("name", chargeName.getText().toString());
        newChargeRef.setValue(newCharge);
        Firebase transactionsRef = new Firebase("https://split-it.firebaseio.com/").child("trips").child(TripActivity.currentTrip.getId()).child("charges").child(newChargeRef.getKey()).child("transaction");
        Map<String, String> transactions = new HashMap<String, String>();
        transactions.put(TripActivity.currentUser.getID(), chargeAmount.getText().toString());
        transactionsRef.setValue(transactions);
        Intent i = new Intent(AddChargeActivity.this, OverviewActivity.class);
        startActivity(i);
    }


}
