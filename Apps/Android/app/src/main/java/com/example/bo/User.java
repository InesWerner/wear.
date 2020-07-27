package com.example.bo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable {


    private int userID;
    private String username;
    private String name;
    private String mail;
    private String password;
    private Bitmap profilImage;
    private boolean createStatus;
    private boolean loginStatus;


    public User(){

    }

    public User(int userID, String username, String name, String mail) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.mail = mail;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String toString(){
        return this.userID + " " + this.username + " " + this.name + " " + this.mail + " " + this.profilImage;
    }

    public boolean isCreateStatus() {
        return createStatus;
    }

    public void setCreateStatus(boolean createStatus) {
        this.createStatus = createStatus;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Bitmap getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(Bitmap profilImage) {
        this.profilImage = profilImage;
    }
}
