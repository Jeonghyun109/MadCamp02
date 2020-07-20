package com.example.test1.ui.contact;

public class ContactInfo {
    private String Name;
    private String email;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public ContactInfo(String name, String email) {
        Name = name;
        this.email = email;
    }
}
