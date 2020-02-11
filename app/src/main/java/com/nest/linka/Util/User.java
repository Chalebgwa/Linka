package com.nest.linka.Util;

import android.net.Uri;

/**
 * Created by black on 2/20/17.
 */

public class User {

    private String username;
    private String userId;
    private Uri photo;

    public User(String username){
        this.setUsername(username);
    }
    public User(String username,String userId){
        this.setUsername(username);
        this.setUserId(userId);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
}
