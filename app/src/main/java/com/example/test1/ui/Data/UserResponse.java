package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}