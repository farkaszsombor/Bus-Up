package com.example.zsombor.bus_up;

/**
 * Created by Joco on 2017. 11. 14..
 */

public class Bustime {

    String to_loc;
    String from_loc;

    public Bustime(){

    }

    public Bustime(String to_loc, String from_loc) {
        this.to_loc = to_loc;
        this.from_loc = from_loc;
    }

    public String getTo_loc() {
        return to_loc;
    }

    public void setTo_loc(String to_loc) {
        this.to_loc = to_loc;
    }

    public String getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc(String from_loc) {
        this.from_loc = from_loc;
    }

}
