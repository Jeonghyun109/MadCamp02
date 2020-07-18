package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class ContactData {
    @SerializedName("userID")
    private String userID;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPhone")
    private String userPhone;

    public ContactData(String userID, String userName, String userEmail, String userPhone) {
        this.userID= userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }
}