package com.splitit.splitit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
    public static ArrayList<String> chargeName = new ArrayList<>();
    public static ArrayList<String> chargeValue = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> everyCharge = new ArrayList<>();
    private String[] chargePerson = {"Crishna", "ashay", "Eric"};
    private ArrayList<String> tripIds = new ArrayList<>();
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


        System.out.println(chargeName.size() + " OIHSDFOSD    " + chargeValue.size());
        chargeList = (ListView) findViewById(R.id.charges_listView);

        chargeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openInformation(view);

                // ListView Clicked item index
                /*int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                // Show Alert
                Intent i = new Intent(OverviewActivity.this, OverviewActivity.class);
                currentTrip = trips.get(itemPosition);


                startActivity(i);*/
            }
        

        });

        showActivity();

    }

    public void openInformation(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OverviewActivity.this);

        alertDialogBuilder.setTitle(this.getTitle()+ " decision");
        alertDialogBuilder.setMessage("Are you sure?");
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // go to a new activity of the app
                Intent positveActivity = new Intent(getApplicationContext(),
                        OverviewActivity.class);
                startActivity(positveActivity); 
            }
          });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "You chose a negative answer",
                        Toast.LENGTH_LONG).show();
            }
        });
        // set neutral button: Exit the app message
        alertDialogBuilder.setNeutralButton("Exit the app",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // exit the app and go to the HOME
                OverviewActivity.this.finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
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
