package com.example.zsombor.bus_up;

/**
 * Created by Zsombor on 11/17/2017.
 */

public class Bus {

    private String from_loc;
    private String to_loc;
    private String iconUrl;
    public Bus(){

    }

    public Bus(String from_loc, String to_loc,String iconUrl) {
        this.from_loc = from_loc;
        this.to_loc = to_loc;
        this.iconUrl=iconUrl;
    }

    public String getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc(String from_loc) {
        this.from_loc = from_loc;
    }

    public String getTo_loc() {
        return to_loc;
    }

    public void setTo_loc(String to_loc) {
        this.to_loc = to_loc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
