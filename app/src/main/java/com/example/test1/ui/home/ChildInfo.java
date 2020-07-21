package com.example.test1.ui.home;

public class ChildInfo {
    private String r_name;
    private String r_timestamp;
    private String r_content;

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getR_timestamp() {
        return r_timestamp;
    }

    public void setR_timestamp(String r_timestamp) {
        this.r_timestamp = r_timestamp;
    }

    public String getR_content() {
        return r_content;
    }

    public void setR_content(String r_content) {
        this.r_content = r_content;
    }

    public ChildInfo(String r_name, String r_timestamp, String r_content) {
        this.r_name = r_name;
        this.r_timestamp = r_timestamp;
        this.r_content = r_content;
    }
}
