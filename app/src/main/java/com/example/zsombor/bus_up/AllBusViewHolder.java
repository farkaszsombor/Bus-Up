package com.example.zsombor.bus_up;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Joco on 2017. 12. 06..
 */

public class AllBusViewHolder extends RecyclerView.ViewHolder {

    private TextView to_from;
    private TextView from_to;
    private ImageView logo;

    public AllBusViewHolder(View itemView) {
        super(itemView);
        from_to = itemView.findViewById(R.id.location_to_from);
        to_from = itemView.findViewById(R.id.location_from_to);
        logo = itemView.findViewById(R.id.icon_rec);
    }

    public TextView getTo_from() {
        return to_from;
    }

    public void setTo_from(TextView to_from) {
        this.to_from = to_from;
    }

    public TextView getFrom_to() {
        return from_to;
    }

    public void setFrom_to(TextView from_to) {
        this.from_to = from_to;
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }


}
