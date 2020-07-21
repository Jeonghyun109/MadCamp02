package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class HomeData {
    @SerializedName("userID")
    private int userID;

    @SerializedName("hostName")
    private String hostName;

    @SerializedName("hostComm")
    private String hostComm;

    public HomeData(String hostName) { this.hostName=hostName; }

    public HomeData(String hostName, String hostComm) {
        this.hostName=hostName;
        this.hostComm=hostComm;

    }

    public int getId(){ return userID; }
}