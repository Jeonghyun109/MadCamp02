package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class ContactData {
    @SerializedName("userName")
    private String userName;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPhone")
    private String userPhone;

    @SerializedName("userID")
    private int userID;

    public ContactData(int userID, String userName, String userPhone, String userEmail) {
        this.userID=userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public ContactData(int userID) {
        this.userID=userID;
    }
}