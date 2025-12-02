package com.example.proiectul.Cinema.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="movies")

public class Movie {
    @Id
    @Column(length = 50)
    private String id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false, length = 200)
    private String title;

    @Positive(message = "Duration must be positive")
    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @PastOrPresent(message = "Release date cannot be in the future")
    @Column(name = "release_date")
    private LocalDate release_date;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Screening> screenings = new ArrayList<>();

    public Movie() { }

    public Movie(String id, String title, int durationMinutes, LocalDate release_date) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.release_date = release_date;
    }
    public LocalDate getRelease_date() {return release_date;}
    public void setRelease_date(LocalDate release_date) {this.release_date = release_date;}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public List<Screening> getScreenings() { return screenings; }
    public void setScreenings(List<Screening> screenings) { this.screenings = screenings; }
}

