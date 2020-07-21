package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class HomeData {
    @SerializedName("hostName")
    private String hostName;

    @SerializedName("hostEmail")
    private String hostEmail;

    @SerializedName("hostPhoto")
    private String hostPhoto;

    @SerializedName("hostComm")
    private String hostComm;

    public HomeData(String hostName) { this.hostName=hostName; }

    public HomeData(String hostName, String hostEmail, String hostComm, String hostPhoto){
        this.hostName=hostName;
        this.hostComm=hostComm;
        this.hostEmail=hostEmail;
        this.hostPhoto=hostPhoto;
    }
    public HomeData(String hostName, String hostComm) {
        this.hostName=hostName;
        this.hostComm=hostComm;

    }

}