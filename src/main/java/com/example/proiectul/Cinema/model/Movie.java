package com.example.proiectul.Cinema.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String Id;
    private String title;
    private int durationMinutes;
    private LocalDate release_date;

    private List<Screening> screenings = new ArrayList<>();

    public Movie() { }

    public Movie(String id, String title, int durationMinutes, LocalDate release_date) {
        this.Id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.release_date = release_date;
    }
    public LocalDate getRelease_date() {return release_date;}
    public void setRelease_date(LocalDate release_date) {this.release_date = release_date;}

    public String getId() { return Id; }
    public void setId(String id) { this.Id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public List<Screening> getScreenings() { return screenings; }
    public void setScreenings(List<Screening> screenings) { this.screenings = screenings; }
}

