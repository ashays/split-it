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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OverviewActivity extends AppCompatActivity {

    private ListView chargeList;
    private ArrayList<HashMap<String, String>> chargeArrayList;
    private ListAdapter adapter;
    private HashMap<String, String> chargeMap;
    private ArrayList<String> chargeName = new ArrayList<>();
    private ArrayList<String> chargeValue = new ArrayList<>();
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
        final Firebase ref2 = new Firebase("https://split-it.firebaseio.com/");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        //currentTrip = new Trip(currentTripId);
        myFirebaseRef.child("trips").child(currentTripId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                boolean credit = false;
                for (DataSnapshot transaction: snapshot.getChildren()) {
                    System.out.println("LOOK HERE" + transaction.child(currentLoginId).exists());
                    if (transaction.child(currentLoginId).exists()) {
                        chargeName.add((String) snapshot.child("name").getValue());
                        chargeValue.add((String) transaction.child(currentLoginId).getValue());
                    }
                }
            }

            @Override
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
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });


        chargeList = (ListView) findViewById(R.id.charges_listView);
        showActivity();

    }

    public void showActivity() {

            chargeArrayList = new ArrayList<HashMap<String, String>>();
   
            
            /********************************************************/
            
            
            /**********Display the contents************/
            System.out.println("chargeNameSize" + chargeName.size());

            for (int i = 0; i < chargeName.size(); i++) {
                chargeMap = new HashMap<String, String>();

                chargeMap.put("one", chargeName.get(i));
                System.out.println(chargeName.get(i));
                chargeMap.put("three", chargeValue.get(i));
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
