package com.example.proiectul.Cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@MappedSuperclass
public abstract class Staff {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required")
    private String id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private Integer salary;

    public Staff() {}

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
