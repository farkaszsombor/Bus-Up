package com.example.zsombor.bus_up;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Janosi on 12/4/2017.
 */

public class User {
    String name,email,photoURL,nextbutton;




    public User() {
    }

    public User(String name, String email, String photoURL, String nextbutton) {
        this.name = name;
        this.email = email;
        this.photoURL = photoURL;
        this.nextbutton = nextbutton;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getNextbutton() {
        return nextbutton;
    }

    public void setNextbutton(String nextbutton) {
        this.nextbutton = nextbutton;
    }

}
