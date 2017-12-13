package com.example.zsombor.bus_up;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.Path;
import java.util.ArrayList;


public class BusDetaliedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textView;//
    private  String data;
    private TextView stations;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private TableLayout hour;
    private TableLayout hour2;
    private TableLayout hour3;
    private TableLayout hour4;

    private ArrayList<String> dateCt;

    public BusDetaliedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dateCt = new ArrayList<>();
        data = (String) getArguments().get("linia");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bus_detalied, container, false);
        stations = view.findViewById(R.id.stations);//inic
        hour = view.findViewById(R.id.hour);
        hour2 = view.findViewById(R.id.hour2);
        hour3 = view.findViewById(R.id.hour3);
        hour4 = view.findViewById(R.id.hour4);
        databaseReference.child("Bus").child(data).child("Path_Stations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                        String stationKey= data.getValue(String.class);
                        databaseReference.child("Stations").child(stationKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String stationName = dataSnapshot.child("stationName").getValue(String.class);
                                stations.append(stationName + "-");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference.child("Bus").child(data).child("Path_Stations_Retur").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot data : dataSnapshot.getChildren()){
                    final String stationKey= data.getValue(String.class);
                    databaseReference.child("Stations").child(stationKey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String stationName = dataSnapshot.child("stationName").getValue(String.class);
                            stations.append(stationName + "-");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.child("Bus").child("linia 26").orderByValue().equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String hourKey = data.getKey();
                    Log.e("KeysSET",hourKey);
                    databaseReference.child("Dates").child(hourKey).child("dates_weekdays_tur").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String hourData = dataSnapshot.getKey();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String date = snapshot.getKey();
                                dateCt.add(date);
                            }
                            int j = 0;
                            while(j < dateCt.size()){

                                TableRow row = new TableRow(getActivity().getApplicationContext());
                                TableRow.LayoutParams ep = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(ep);
                                for(int i=0; i<6; i++){
                                    TextView textView = new TextView(getActivity().getApplicationContext());
                                    if(j >= dateCt.size()){
                                        break;
                                    }
                                    textView.setText(dateCt.get(j));
                                    row.addView(textView);
                                    ++j;
                                }
                                hour.addView(row);

                            }
                            dateCt.clear();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //

        databaseReference.child("Bus").child("linia 26").orderByValue().equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String hourKey = data.getKey();
                    Log.e("KeysSET",hourKey);
                    databaseReference.child("Dates").child(hourKey).child("dates_weekdays_retur").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String hourData = dataSnapshot.getKey();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String date = snapshot.getKey();
                                dateCt.add(date);
                            }
                            int j = 0;
                            while(j < dateCt.size()){

                                TableRow row = new TableRow(getActivity().getApplicationContext());
                                TableRow.LayoutParams ep = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(ep);
                                for(int i=0; i<6; i++){
                                    TextView textView = new TextView(getActivity().getApplicationContext());
                                    if(j >= dateCt.size()){
                                        break;
                                    }
                                    textView.setText(dateCt.get(j));
                                    row.addView(textView);
                                    ++j;
                                }
                                hour2.addView(row);

                            }
                            dateCt.clear();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference.child("Bus").child("linia 26").orderByValue().equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String hourKey = data.getKey();
                    Log.e("KeysSET",hourKey);
                    databaseReference.child("Dates").child(hourKey).child("dates_weekend_retur").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String hourData = dataSnapshot.getKey();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String date = snapshot.getKey();
                                dateCt.add(date);
                            }
                            int j = 0;
                            while(j < dateCt.size()){

                                TableRow row = new TableRow(getActivity().getApplicationContext());
                                TableRow.LayoutParams ep = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(ep);
                                for(int i=0; i<6; i++){
                                    TextView textView = new TextView(getActivity().getApplicationContext());
                                    if(j >= dateCt.size()){
                                        break;
                                    }
                                    textView.setText(dateCt.get(j));
                                    row.addView(textView);
                                    ++j;
                                }
                                hour3.addView(row);

                            }
                            dateCt.clear();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.child("Bus").child("linia 26").orderByValue().equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String hourKey = data.getKey();
                    Log.e("KeysSET",hourKey);
                    databaseReference.child("Dates").child(hourKey).child("dates_weekend_tur").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String hourData = dataSnapshot.getKey();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String date = snapshot.getKey();
                                dateCt.add(date);
                            }
                            int j = 0;
                            while(j < dateCt.size()){

                                TableRow row = new TableRow(getActivity().getApplicationContext());
                                TableRow.LayoutParams ep = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(ep);
                                for(int i=0; i<6; i++){
                                    TextView textView = new TextView(getActivity().getApplicationContext());
                                    if(j >= dateCt.size()){
                                        break;
                                    }
                                    textView.setText(dateCt.get(j));
                                    row.addView(textView);
                                    ++j;
                                }
                                hour4.addView(row);

                            }
                            dateCt.clear();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



    }
}
