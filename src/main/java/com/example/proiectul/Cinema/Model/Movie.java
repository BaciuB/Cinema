package com.example.proiectul.Cinema.Model;

import java.util.List;

public class Movie {
    private String Id;
    private String title;
    private int durationMinutes;

    private List<Screening> screenings;

    public Movie() { }

    public Movie(String id, String title, int durationMinutes) {
        this.Id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    public String getId() { return Id; }
    public void setId(String id) { this.Id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public List<Screening> getScreenings() { return screenings; }
    public void setScreenings(List<Screening> screenings) { this.screenings = screenings; }
}

