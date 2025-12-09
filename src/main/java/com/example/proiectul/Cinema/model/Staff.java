package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "staff")
@Inheritance(strategy = InheritanceType.JOINED)
// Poate trebe DiscriminatorColumn dar nu sunt sigur (uita-te la seminarul 8)
public abstract class Staff {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required")
    private String id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(length = 20, nullable = false)
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private Integer salary;   // <--- schimbat din int în Integer

    public Staff() { }

    public Staff(String id, String name, Integer salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getSalary() { return salary; }
    public void setSalary(Integer salary) { this.salary = salary; }
}
