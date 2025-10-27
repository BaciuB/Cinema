package com.example.proiectul.Cinema.Model;

import java.time.LocalDate;
import java.util.List;

public class Movie {
    private Long id;
    private String title;
    private String genre;
    private LocalDate releaseDate;
    private int durationMinutes;
    private double rating;

    private List<Actor> actors; // relație simplă

    public Movie() { }

    public Movie(Long id, String title, String genre, LocalDate releaseDate,
                 int durationMinutes, double rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.durationMinutes = durationMinutes;
        this.rating = rating;
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public List<Actor> getActors() { return actors; }
    public void setActors(List<Actor> actors) { this.actors = actors; }

    @Override
    public String toString() {
        return title + " (" + genre + ", " + releaseDate.getYear() + ")";
    }
}

