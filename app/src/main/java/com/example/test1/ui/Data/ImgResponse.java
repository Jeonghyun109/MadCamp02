package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class ImgResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}