package com.example.proiectul.Cinema.model;

public class SupportStaff extends Staff {
    private String role;

    public SupportStaff() { }

    public SupportStaff(String Id, String name, String role) {
        super(Id, name);
        this.role = role;
    }

    public String getRole() {return role; }
    public void setRole(String role) { this.role = role; }

}

