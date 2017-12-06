package com.example.zsombor.bus_up;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zsombor on 11/17/2017.
 */

public class BusViewHolder extends RecyclerView.ViewHolder{

    public TextView mFromTo;
    public TextView mToFrom;
    public ImageView icon;
    private BusClickListenerInterface mClickListener;

    public BusViewHolder(View itemView) {
        super(itemView);
        mFromTo = itemView.findViewById(R.id.location_to_from);
        mToFrom = itemView.findViewById(R.id.location_from_to);
        icon = itemView.findViewById(R.id.icon_rec);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
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

    public void setmClickListener(BusClickListenerInterface listener){
        this.mClickListener = listener;
    }
}