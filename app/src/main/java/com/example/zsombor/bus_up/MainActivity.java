package com.example.zsombor.bus_up;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity{

    private Button mButon;
    private TextView mtextView;
    ImageButton imageButton;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    switch (item.getItemId()) {
                        case R.id.path_from_to:
                            transaction.replace(R.id.container, new Utvonal_terv()).commit();
                            return true;
                        case R.id.every_bus:
                           transaction.replace(R.id.container, new Osszes_busz()).commit();
                            return true;
                        case R.id.profile:
                            transaction.replace(R.id.container, new Profile()).commit();
                            return true;
                        case R.id.main_page:
                            transaction.replace(R.id.container,new MainPageFragment()).commit();
                            return true;
                    }
                    return false;
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();


    }






}




