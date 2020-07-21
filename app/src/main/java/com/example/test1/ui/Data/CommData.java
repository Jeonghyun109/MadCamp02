package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class CommData {
    @SerializedName("postID")
    private int postID;

    @SerializedName("commName")
    private String commName;

    @SerializedName("commCont")
    private String commCont;

    @SerializedName("time")
    private String time;

    @SerializedName("commID")
    private int commID;

    public CommData(int postID, String commName, String commCont, String time, int commID) {
        this.postID=postID;
        this.commName=commName;
        this.commCont=commCont;
        this.time=time;
        this.commID=commID;
    }

    public CommData(String commName, String time) {
        this.commName=commName;
        this.time=time;
    }

    public CommData(int postID) {
        this.postID=postID;
    }

    public int getId(){ return postID; }
}
