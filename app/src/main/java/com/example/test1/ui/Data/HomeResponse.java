package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class HomeResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("json")
    private String json;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getJson() { return json; }
}
