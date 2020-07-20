package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class GalleryData {
    @SerializedName("userID")
    private int userID;

    @SerializedName("URI")
    private String URI;

    public GalleryData(int userID, String uri){
        this.userID=userID;
        this.URI=uri;
    }

    public GalleryData(int userID){ this.userID=userID; }

    public GalleryData(String URI){ this.URI=URI; }

    public int getID(){ return userID; }
}
