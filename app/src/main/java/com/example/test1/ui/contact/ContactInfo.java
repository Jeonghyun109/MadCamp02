package com.example.test1.ui.contact;

public class ContactInfo {
    private String Name;
    private String phNumber;
    private String email;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public ContactInfo(String name, String phNumber, String email) {
        Name = name;
        this.phNumber = phNumber;
        this.email = email;
    }
}
