package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "support_staff")
public class SupportStaff extends Staff {
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Role role;

    public SupportStaff() { }

    public SupportStaff(String Id, String name, int salary, Role role) {
        super(Id, name, salary);
        this.role = role;
    }

    public Role getRole() {return role; }
    public void setRole(Role role) { this.role = role; }

}

