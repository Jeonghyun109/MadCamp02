package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class ContactData {
    @SerializedName("userName")
    private String userName;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userID")
    private int userID;

    public ContactData(int userID, String userName, String userEmail) {
        this.userID=userID;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public ContactData(int userID, String userName) {
        this.userID=userID;
        this.userName = userName;
    }

    public ContactData(String userName) {
        this.userName=userName;
    }
    public ContactData(int userID) { this.userID=userID; }

    public int getId(){ return userID; }
    public String getName(){ return userName; }
}