package com.example.proiectul.Cinema.Model;

import java.time.LocalDate;

public class Actor {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String nationality;

    public Actor() { }

    public Actor(Long id, String name, LocalDate birthDate, String nationality) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }

}

