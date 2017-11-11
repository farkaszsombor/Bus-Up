package com.example.zsombor.bus_up;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

   private Button mButon;
   private TextView mtextView;

    private  BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    {
        onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
             /*   case R.id.main_page:
                    Kezdooldal kezdooldal = new Kezdooldal();
                    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.Kezdooldal_id,kezdooldal).commit();
                    return true;*/


                    case R.id.path_from_to:
                        Osszes_busz osszes_busz = new Osszes_busz();
                        android.support.v4.app.FragmentManager manager2 = getSupportFragmentManager();
                        manager2.beginTransaction().replace(R.id.osszes_bus_id, osszes_busz).commit();
                        return true;


                    case R.id.every_bus:

                        return true;
                }
                return false;

            }

        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);



    }}




