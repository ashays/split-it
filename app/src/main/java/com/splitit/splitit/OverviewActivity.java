package com.splitit.splitit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OverviewActivity extends AppCompatActivity {

    private ListView chargeList, chargeTitleList;
    private ArrayList<HashMap<String, String>> chargeArrayList, chargeTitleArrayList;
    private ListAdapter adapter, adapter_title;
    private HashMap<String, String> chargeMap, chargeTitleMap;
    private String[] chargeName = {"Dinner", "movies", "Dinner again"};
    private String[] chargeValue = {"9.54", "8.34", "-5.43"};
    private String[] chargePerson = {"Crishna", "ashay", "Eric"};
    private ArrayList<String> tripIds = new ArrayList<>();
    private String currentTripId = "-KAy68tW4PoGNyVPAMhi";
    private String currentLoginId = "bob";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://split-it.firebaseio.com/");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //currentTrip = new Trip(currentTripId);
        /*myFirebaseRef.child("trips").child(currentTripId).child("charges").addValueEventListener(new ValueEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                boolean credit = false;
                for (DataSnapshot person: snapshot.child("transaction").getChildren()) {
                    if (person.getKey().equals(currentLoginId)) {
                        if (Integer.parseInt(person.getValue()) > 0) {
                            credit = true;

                        }
                        if (credit) {

                        }
                        chargeName.add(snapshot.child("name"));
                        chargeValue.add()
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
            }
        });*/


        chargeList = (ListView) findViewById(R.id.charges_listView);
        chargeTitleList = (ListView) findViewById(R.id.charges_header);
        showActivity();

    }

    public void showActivity() {

            chargeArrayList = new ArrayList<HashMap<String, String>>();
            chargeTitleArrayList = new ArrayList<HashMap<String, String>>();
            
            /**********Display the headings************/
            
            
            chargeTitleMap = new HashMap<String, String>();

            chargeTitleMap.put("one", "Chage name");
            chargeTitleMap.put("two", "Details");
            chargeTitleMap.put("three", "Amount");
            chargeTitleArrayList.add(chargeTitleMap);

            
            
            try {
                adapter_title = new SimpleAdapter(this, chargeTitleArrayList, R.layout.row,
                        new String[] { "one", "two", "three" }, new int[] {
                                R.id.one, R.id.two, R.id.three });
                chargeTitleList.setAdapter(adapter_title);
            } catch (Exception e) {
                
            }
            
            /********************************************************/
            
            
            /**********Display the contents************/
            
            for (int i = 0; i < chargeName.length; i++) {
                chargeMap = new HashMap<String, String>();

                chargeMap.put("one", chargeName[i]);
                chargeMap.put("three", chargeValue[i]);
                chargeArrayList.add(chargeMap);
            }

            
            try {
                adapter = new SimpleAdapter(this, chargeArrayList, R.layout.row,
                        new String[] { "one", "two", "three" }, new int[] {
                                R.id.one, R.id.two, R.id.three });
                chargeList.setAdapter(adapter);
            } catch (Exception e) {
                
            }
            
            /********************************************************/

    }

}
