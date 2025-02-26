package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("userName")
    private String userName;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPwd")
    private String userPwd;

    public JoinData(String userName, String userEmail, String userPwd) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPwd = userPwd;
    }

    public String getName(){ return userName; }
    public String getEmail(){ return userEmail; }
}