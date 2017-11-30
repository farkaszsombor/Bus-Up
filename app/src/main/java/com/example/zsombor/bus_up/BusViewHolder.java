package com.example.zsombor.bus_up;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zsombor on 11/17/2017.
 */

public class BusViewHolder extends RecyclerView.ViewHolder{

    public TextView mFromTo;
    public  TextView mToFrom;

    public BusViewHolder(View itemView) {
        super(itemView);
        mFromTo = itemView.findViewById(R.id.location_to_from);
        mToFrom = itemView.findViewById(R.id.location_from_to);
    }

    public TextView getmFromTo() {
        return mFromTo;
    }

    public void setmFromTo(TextView mFromTo) {
        this.mFromTo = mFromTo;
    }

    public TextView getmToFrom() {
        return mToFrom;
    }

    public void setmToFrom(TextView mToFrom) {
        this.mToFrom = mToFrom;
    }
}