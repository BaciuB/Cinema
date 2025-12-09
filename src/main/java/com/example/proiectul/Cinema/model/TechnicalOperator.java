package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "technical_operators")
public class TechnicalOperator extends Staff {

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    @NotNull(message = "Specialization is required")
    private Specialization specialization;

    public TechnicalOperator() {
        // constructor fără argumente pentru JPA
    }

    public TechnicalOperator(String id, String name, Integer salary, Specialization specialization) {
        super(id, name, salary);   // salary este Integer în Staff
        this.specialization = specialization;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
