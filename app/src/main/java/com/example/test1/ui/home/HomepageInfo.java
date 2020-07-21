package com.example.test1.ui.home;

public class HomepageInfo {
    private int b_number;
    private String b_name;
    private String timestamp;
    private String img_uri;
    private String b_content;

    public int getB_number() {
        return b_number;
    }

    public void setB_number(int b_number) {
        this.b_number = b_number;
    }

    public String getB_Name() {
        return b_name;
    }

    public void setB_Name(String name) {
        this.b_name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }

    public String getB_content() {
        return b_content;
    }

    public void setB_content(String b_content) {
        this.b_content = b_content;
    }

    public HomepageInfo(int b_number, String name, String timestamp, String img_uri, String b_content) {
        this.b_number = b_number;
        this.b_name = name;
        this.timestamp = timestamp;
        this.img_uri = img_uri;
        this.b_content = b_content;
    }

    public HomepageInfo(int b_number, String name, String timestamp, String b_content) {
        this.b_number = b_number;
        this.b_name = name;
        this.timestamp = timestamp;
        this.b_content = b_content;
    }
}
