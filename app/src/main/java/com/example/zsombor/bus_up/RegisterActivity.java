package com.example.zsombor.bus_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
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


        mRegisterButton = findViewById(R.id.button_register);
        mPassword = findViewById(R.id.password_register);
        mPasswordSecond = findViewById(R.id.repassword_register);
        mEmail = findViewById(R.id.email_register);
        mFullName = findViewById(R.id.name_register);
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
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception ex) {
                            if(ex instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegisterActivity.this,getResources().getText(R.string.user_already),Toast.LENGTH_LONG).show();
                            } else if (ex instanceof FirebaseException) {
                                Toast.makeText(RegisterActivity.this,"Hiba tortent probld ujra",Toast.LENGTH_LONG).show();
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

        boolean valid = true;
        if(mFullName.getText().toString().equals("")){
            mFullName.setError(getResources().getString(R.string.empty_field));
            valid = false;
        }else if (mEmail.getText().toString().equals("")){
            mEmail.setError(getResources().getString(R.string.empty_field));
            valid = false;
        }else if (mPassword.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this,getResources().getText(R.string.empty_field),Toast.LENGTH_LONG).show();
            valid = false;
        }else if(mPasswordSecond.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this,getResources().getText(R.string.empty_field),Toast.LENGTH_LONG).show();
            valid = false;
        }else if (!mPassword.getText().toString().equals(mPasswordSecond.getText().toString())){
            Toast.makeText(RegisterActivity.this,getResources().getText(R.string.no_match_password),Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }
}
