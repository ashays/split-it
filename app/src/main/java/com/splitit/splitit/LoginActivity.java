package com.splitit.splitit;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import java.lang.String;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends FirebaseLoginBaseActivity {

    private static boolean isStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton fblogin = (ImageButton) findViewById(R.id.fblogin);
        fblogin.setOnClickListener(new View.OnClickListener() {
            //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            //fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFirebaseLoginPrompt();
                isStart = false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setEnabledAuthProvider(AuthProviderType.FACEBOOK);

    }

    @Override
    public Firebase getFirebaseRef() {
        Firebase.setAndroidContext(this);
        return new Firebase("https://split-it.firebaseio.com/");
    }

    @Override
    public void onFirebaseLoginProviderError(FirebaseLoginError firebaseError) {
        Context context = getApplicationContext();
        CharSequence text = "Login Failed";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {
        Context context = getApplicationContext();
        CharSequence text = "Login Failed";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onFirebaseLoggedIn(final AuthData authData) {
        if (isStart) {
            return;
        }
        String uid = authData.getUid();
        Map<String, Object> pd = authData.getProviderData();
        String[] pieces = ((String)pd.get("displayName")).split(" ");;
        TripActivity.currentUser = new Person(pieces[0], pieces[1], uid.toString());
        System.out.println(TripActivity.currentUser.getFirstName() + " " + TripActivity.currentUser.getLastName());
        Firebase firebaseRef = new Firebase("https://split-it.firebaseio.com/");
        Firebase userRef = firebaseRef.child("users").child(uid);
        userRef.child("firstName").setValue(TripActivity.currentUser.getFirstName());
        userRef.child("lastName").setValue(TripActivity.currentUser.getLastName());
        isStart = true;
        userRef.child("trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot trip : snapshot.getChildren()) {
                    String theTripID = (String) trip.getValue();
                    System.out.println("Trip ID: " + theTripID);
                    // TripActivity.currentUser.addTrip(new Trip(theTripID));
                    Firebase tripRefTemp = new Firebase("https://split-it.firebaseio.com/").child("trips").child(theTripID);
                    tripRefTemp.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            TripActivity.currentUser.addTrip(new Trip((String) snapshot.child("name").getValue()));
                            //TripActivity.listValues = Arrays.copyOf(TripActivity.listValues, TripActivity.listValues.length + 1);
                            //TripActivity.listValues[TripActivity.listValues.length - 1] = (String) snapshot.child("name").getValue();
                            //TripActivity.adapter.notifyDataSetChanged();
                            System.out.println("Trip Snapshot: " + snapshot.child("name").getValue());
                            //TripActivity.refreshTrips();
                            // System.out.println(snapshot.getValue());
                            //System.out.println(snapshot.getValue(Trip.class));
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("There was an error getting the user's trips (one of them at least");
                        }
                    });
                }
                System.out.println("There are " + TripActivity.currentUser.getTrips() + " in arraylist.");
                Intent i = new Intent(LoginActivity.this, TripActivity.class);
                startActivity(i);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("There was an error getting the user");
            }
        });
    }

    @Override
    public void onFirebaseLoggedOut() {
        //Intent i = new Intent(LoginActivity.this, TripActivity.class);
        //startActivity(i);
    }


}