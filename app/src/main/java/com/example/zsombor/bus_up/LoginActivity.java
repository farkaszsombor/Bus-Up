package com.example.zsombor.bus_up;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Zsombor on 11/2/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ImageView mbutton;
    private EditText mEmailText,mPasswordText;
    private TextView mRegisterTextview;
    private ConstraintLayout mConstraintLayout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        authWithEmailAndPassword();
        mRegisterTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }


    //Method for initalizing every aspect of design
    @SuppressLint("ClickableViewAccessibility")
    private void initializeViews(){

        mConstraintLayout = findViewById(R.id.login_container);
        mbutton = findViewById(R.id.login_button);
        mEmailText = findViewById(R.id.login_email);
        mRegisterTextview = findViewById(R.id.button_to_register);
        mPasswordText = findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();
        mAuthstateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    finish();
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    private void authWithEmailAndPassword(){

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loginIntoFirebase();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthstateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth != null){
            mAuth.removeAuthStateListener(mAuthstateListener);
        }
    }

    private void loginIntoFirebase(){
        if(validCredentialsEntered()) {
            mAuth.signInWithEmailAndPassword(mEmailText.getText().toString(), mPasswordText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.e(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception ex) {
                    if (ex instanceof FirebaseAuthInvalidCredentialsException) {
                        mEmailText.setError(getResources().getString(R.string.incorrect_credentials));
                        mEmailText.requestFocus();
                    } else if (ex instanceof FirebaseAuthInvalidUserException) {
                        mEmailText.setError(getResources().getString(R.string.no_user_found));
                    } else if (ex instanceof FirebaseException) {
                        Toast.makeText(LoginActivity.this,"Hiba tortent probld ujra",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean validCredentialsEntered(){

        boolean validationControl = true;
        if(mEmailText.getText().toString().equals("")){
            mEmailText.setError(getResources().getString(R.string.empty_field));
            validationControl = false;
        }
        if(mPasswordText.getText().toString().equals("")){
            mPasswordText.setError(getResources().getString(R.string.empty_field));
            validationControl = false;
        }

        return validationControl;
    }


}
