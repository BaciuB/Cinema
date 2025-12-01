package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theatres")
public class Theatre {

    @Id
    @Column(length = 50)
    private String id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String city;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<Hall> halls = new ArrayList<>();

    public Theatre() {
    }

    public Theatre(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }
}
