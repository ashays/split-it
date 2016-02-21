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

import java.util.Map;


public class LoginActivity extends FirebaseLoginBaseActivity {

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
                facebookLogin();
            }
        });
    }


    public void facebookLogin() {
        showFirebaseLoginPrompt();
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
    public void onFirebaseLoggedIn(AuthData authData) {
        Intent i = new Intent(LoginActivity.this, TripActivity.class);
        TripActivity.uid = authData.getUid();
        Map<String, Object> pd = authData.getProviderData();
        String[] pieces = ((String)pd.get("displayName")).split(" ");;
        TripActivity.currentUser = new Person(pieces[0], pieces[1], TripActivity.uid.toString());
        startActivity(i);
        /*
        Firebase fb = getFirebaseRef();
        final Firebase tripsRef = fb.child("trips");
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
                            String tripName = (String) postSnapshot.child("name").getValue();
                            Trip newTrip = new Trip(tripName);
                            newTrip.setId(postSnapshot.getKey());
                            TripActivity.currentUser.addTrip(newTrip);
                        }
                        Intent i = new Intent(LoginActivity.this, TripActivity.class);
                        Map<String, Object> pd = authData
                        startActivity(i);
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }
        });
         */

    }

    @Override
    public void onFirebaseLoggedOut() {
        //Intent i = new Intent(LoginActivity.this, TripActivity.class);
        //startActivity(i);
    }


}
