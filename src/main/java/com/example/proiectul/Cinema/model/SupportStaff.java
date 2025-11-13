package com.example.proiectul.Cinema.model;

public class SupportStaff extends Staff {
    private Role role;

    public SupportStaff() { }

    public SupportStaff(String Id, String name, int salary, Role role) {
        super(Id, name, salary);
        this.role = role;
    }

    public Role getRole() {return role; }
    public void setRole(Role role) { this.role = role; }

}

