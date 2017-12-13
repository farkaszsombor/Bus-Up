package com.example.zsombor.bus_up;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivilegedAction;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


public class Osszes_busz extends Fragment implements BusDetaliedCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView firstbus;
    private TextView busz2;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView myRecycleview;

    private FirebaseRecyclerAdapter<Bus,AllBusViewHolder> mAdapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("Bus");



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_osszes_busz, container, false);

        myRecycleview = (RecyclerView)view.findViewById(R.id.bus_view);
        myRecycleview.setHasFixedSize(true);
        myRecycleview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        FirebaseRecyclerOptions<Bus> options = new FirebaseRecyclerOptions.Builder<Bus>().setQuery(myRef,Bus.class).build();
        mAdapter = new FirebaseRecyclerAdapter<Bus, AllBusViewHolder>(options) {
            @Override
            protected void onBindViewHolder(AllBusViewHolder holder, final int position, Bus model) {
                holder.getTo_from().setText(getResources().getString(R.string.concanetated,model.getFrom_loc(),model.getTo_loc()));
                holder.getFrom_to().setText(getResources().getString(R.string.concanetated,model.getTo_loc(),model.getFrom_loc()));
                Glide.with(getActivity()).load(model.getIconUrl()).into(holder.getLogo());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendData(mAdapter.getRef(position).getKey());
                       // sendData(mAdapter.getRef());
                    }
                });
            }

            @Override
            public AllBusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_row,parent,false);
                final AllBusViewHolder viewHolder = new AllBusViewHolder(view);
                return viewHolder;
            }
        };
        myRecycleview.setAdapter(mAdapter);




        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void sendData(String value) {

        BusDetaliedFragment fragment = new BusDetaliedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("linia",value);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment).commit();
    }




}









