package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class VisitorData {
    @SerializedName("hostID")
    private int hostID;

    @SerializedName("visitName")
    private String visitName;

    @SerializedName("time")
    private String time;

    public VisitorData(String visitName, String time) {
        this.visitName=visitName;
        this.time=time;
    }

    public int getId(){ return hostID; }
}
