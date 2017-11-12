package com.example.zsombor.bus_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Zsombor on 11/2/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText mPassword,mEmail,mPasswordSecond,mFullName;
    private ImageView mRegisterButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = database.getReference();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        registerUser();
    }

    private void initializeViews(){

        mRegisterButton = (ImageView)findViewById(R.id.button_register);
        mPassword = (EditText)findViewById(R.id.password_register);
        mPasswordSecond = (EditText)findViewById(R.id.repassword_register);
        mEmail = (EditText)findViewById(R.id.email_register);
        mFullName = (EditText)findViewById(R.id.name_register);
        mAuth = FirebaseAuth.getInstance();

    }
    private void registerUser(){

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validFields()) {
                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String userID = user.getUid();
                                        writeNewUserIntoDatabase(userID, mEmail.getText().toString(), mFullName.getText().toString());
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    } else {
                                        Log.e(TAG, "Failed with: ", task.getException());
                                    }
                                }
                            });
                }
            }
        });

    }

    private void writeNewUserIntoDatabase(String uid,String email,String name){

        mDatabaseReference.child("Users").child(uid).child("email").setValue(email);
        mDatabaseReference.child("Users").child(uid).child("name").setValue(name);
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
