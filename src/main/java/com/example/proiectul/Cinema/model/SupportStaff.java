package com.example.proiectul.Cinema.model;

public class SupportStaff extends Staff {
    private String role;

    public SupportStaff() { }

    public SupportStaff(String Id, String name, int salary, String role) {
        super(Id, name, salary);
        this.role = role;
    }

    public String getRole() {return role; }
    public void setRole(String role) { this.role = role; }

}

