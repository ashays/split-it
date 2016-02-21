package com.splitit.splitit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OverviewActivity extends AppCompatActivity {

    private ListView chargeList, endListNegative, endListPositive;
    private ArrayList<HashMap<String, String>> chargeArrayList, chargeTotalArrayList;
    private ListAdapter adapter, adapter2;
    private HashMap<String, String> chargeMap, chargeTotalMap;
    public static ArrayList<String> chargeName = new ArrayList<>();
    public static ArrayList<String> chargeValue = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> everyCharge = new ArrayList<>();
    public static ArrayList<Person> peopleAtTrip = new ArrayList<>();
    private String[] chargePerson = {"Crishna", "ashay", "Eric"};
    private ArrayList<String> tripIds = new ArrayList<>();
    private String currentLoginId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://split-it.firebaseio.com/");
        final Firebase ref2 = new Firebase("https://split-it.firebaseio.com/");

        currentLoginId = TripActivity.currentUser.getId();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OverviewActivity.this, AddChargeActivity.class);
                startActivity(i);
            }
        });



        //currentTrip = new Trip(currentTripId);


        System.out.println(chargeName.size() + " OIHSDFOSD    " + chargeValue.size());
        chargeList = (ListView) findViewById(R.id.charges_listView);
        endListPositive = (ListView) findViewById(R.id.charges_totalPositive);
        endListNegative = (ListView) findViewById(R.id.charges_totalNegative);

        chargeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openInformation(view, position);

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

    public void openInformation(View view, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OverviewActivity.this);
        String[] dialogStrings = new String[everyCharge.get(position).keySet().size() + 1];
        String name = chargeName.get(position);
        String money = chargeValue.get(position);
        System.out.println("The everyCharge is" + everyCharge.get(position).toString());
        dialogStrings[0] = "You " + (Integer.parseInt(money) < 0 ? "owe " : "paid ") + money.substring(0) + " for this charge";
        System.out.println(dialogStrings[0]);
        int i = 1;


        for (String key : everyCharge.get(position).keySet()) {
            money = everyCharge.get(position).get(key);
            dialogStrings[i] = key + " " + (Integer.parseInt(money) < 0 ? "owes " : "paid ") + money.substring(1) + " for this charge";
            System.out.println(dialogStrings[i]);
        }


        alertDialogBuilder.setTitle(chargeName.get(position))
                .setItems(dialogStrings, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        System.out.println("reached here");
        alertDialogBuilder.create().show();
        System.out.println("reached here also");

/*







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
        alertDialog.show();*/
    }


    public void showActivity() {

        chargeArrayList = new ArrayList<HashMap<String, String>>();
        chargeTotalArrayList = new ArrayList<HashMap<String, String>>();
        int total = 0;
        for (String charge : chargeValue) {
            total += Integer.parseInt(charge);
        }
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



        chargeTotalMap = new HashMap<String, String>();

        chargeTotalMap.put("one", "Total");
        chargeTotalMap.put("three", String.valueOf(total));
        chargeTotalArrayList.add(chargeTotalMap);
        

        
        try {
            adapter2 = new SimpleAdapter(this, chargeTotalArrayList, R.layout.row,
                    new String[] { "one", "two", "three" }, new int[] {
                            R.id.one, R.id.two, R.id.three });
            //endList.setAdapter(adapter2);
            if (total < 0) {
                endListNegative.setAdapter(adapter2);
            } else {
                endListPositive.setAdapter(adapter2);
            }

        } catch (Exception e) {
            
        }



    }

}
