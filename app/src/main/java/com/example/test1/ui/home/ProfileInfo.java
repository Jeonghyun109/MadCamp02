package com.example.test1.ui.home;

public class ProfileInfo {
    private String p_img;
    private String p_name;
    private String p_email;
    private String p_message;

    public String getP_message() {
        return p_message;
    }

    public void setP_message(String p_message) {
        this.p_message = p_message;
    }

    public String getP_img() {
        return p_img;
    }

    public void setP_img(String p_img) {
        this.p_img = p_img;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_email() {
        return p_email;
    }

    public void setP_email(String p_email) {
        this.p_email = p_email;
    }

    public ProfileInfo(String p_img, String p_name, String p_email, String p_message) {
        this.p_img = p_img;
        this.p_name = p_name;
        this.p_email = p_email;
        this.p_message = p_message;
    }
}