package com.example.test1.ui.Data;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

public class ContactResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("phone")
    private String phone;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("id")
    private int id;

    @SerializedName("json")
    private String json;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPhone() { return phone; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public int getID() { return id; }

    public String getJson() { return json; }
}