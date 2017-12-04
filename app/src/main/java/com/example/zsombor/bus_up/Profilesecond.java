package com.example.zsombor.bus_up;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Janosi on 12/1/2017.
 */

public class Profilesecond  extends AppCompatActivity {
    private static final String TAG = "Profilesecond";
    private EditText mPassword,mEmail,mPasswordSecond,mFullName;
    private ImageView mRegisterButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = database.getReference();
    private FirebaseAuth mAuth;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        initializeViews();
        registerUser();
    }

    private void registerUser() {
    }

    private void initializeViews() {
        mRegisterButton = (ImageView)findViewById(R.id.button_profile);
        mPassword = (EditText)findViewById(R.id.newpassword_profile);
        mPasswordSecond = (EditText)findViewById(R.id.newpasswordujra_profile);
        mEmail = (EditText)findViewById(R.id.email_profil);
        mFullName = (EditText)findViewById(R.id.fullname_profile);
        mAuth = FirebaseAuth.getInstance();
    }

    private boolean validFields(){

        if(mFullName.getText().toString().equals("")){
            return false;
        }else if (mEmail.getText().toString().equals("")){
            return false;
        }else if (mPassword.getText().toString().equals("")
                && mPasswordSecond.getText().toString().equals("")){
            return false;
        }else if (!mPassword.getText().toString().equals(mPasswordSecond.getText().toString())){
            return false;
        }

        return true;
    }
}
