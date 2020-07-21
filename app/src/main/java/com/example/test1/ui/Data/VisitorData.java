package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class VisitorData {
    @SerializedName("hostName")
    private String hostName;

    @SerializedName("photo")
    private String photo;

    @SerializedName("content")
    private String content;

    @SerializedName("visitName")
    private String visitName;

    @SerializedName("visitID")
    private int visitID;

    @SerializedName("time")
    private String time;

    public VisitorData(String hostName, String photo, String content, String visitName, int visitID, String time) {
        this.hostName=hostName;
        this.photo=photo;
        this.content=content;
        this.visitID=visitID;
        this.visitName=visitName;
        this.time=time;
    }

    public VisitorData(String hostName) {
        this.hostName=hostName;
    }

}
