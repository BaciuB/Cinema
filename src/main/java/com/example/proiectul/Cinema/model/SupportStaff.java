package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "support_staff")
public class SupportStaff extends Staff {
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    @NotNull(message = "Role is required")
    private Role role;

    public SupportStaff() { }

    public SupportStaff(String id, String name, int salary, Role role) {
        super(id, name, salary);
        this.role = role;
    }

    public Role getRole() {return role; }
    public void setRole(Role role) { this.role = role; }

}

