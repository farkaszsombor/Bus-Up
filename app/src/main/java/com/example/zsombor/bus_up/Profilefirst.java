package com.example.zsombor.bus_up;

import android.app.usage.NetworkStats;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Janosi on 12/1/2017.
 */

public class Profilefirst extends AppCompatActivity {
    public static final String TAG="Profilefirst";
    private TextView mProfilesettings;
    private FirebaseAuth mAuth;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilefirst);
        initializeViews();
        mProfilesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profilefirst.this,Profilesecond.class));
                finish();
            }
        });
    }

    private void initializeViews() {
        mProfilesettings=(TextView)findViewById(R.id.button_to_profile);
        FirebaseAuth firebaseAuth = null;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            startActivity(new Intent(Profilefirst.this,MainActivity.class));
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            finish();
        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }
}



