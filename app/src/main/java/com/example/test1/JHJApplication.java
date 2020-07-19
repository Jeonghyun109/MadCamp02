package com.example.test1;

import android.app.Application;

public class JHJApplication extends Application {
    private int id;
    private String jsonlist;

    public String getJsonlist() {
        return jsonlist;
    }

    public void setJsonlist(String jsonlist) {
        this.jsonlist = jsonlist;
    }

    public int getId() {return id;}
    public void setId(int id){this.id=id;}
}
