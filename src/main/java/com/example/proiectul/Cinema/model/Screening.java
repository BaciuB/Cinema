package com.example.proiectul.Cinema.model;

import java.time.LocalDateTime;
import java.util.List;

public class Screening {
    private String Id;
    private String hallId;
    private String movieId;
    private LocalDateTime dateTime;
    private List<Ticket> tickets;
    private List<StaffAssignment> assignments;

    public Screening() { }

    public Screening(String id, String hallId, String movieId, LocalDateTime dateTime) {
        this.Id = id;
        this.hallId = hallId;
        this.movieId = movieId;
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

}


