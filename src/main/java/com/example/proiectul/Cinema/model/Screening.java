package com.example.proiectul.Cinema.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Screening {
    private String id;
    private String hallId;
    private String movieId;
    private LocalDate dateTime;
    private List<Ticket> tickets = new ArrayList<>();
    private List<StaffAssignment> assignments = new ArrayList<>();

    public Screening() { }

    public Screening(String id, String hallId, String movieId, LocalDate dateTime) {
        this.id = id;
        this.hallId = hallId;
        this.movieId = movieId;
        this.dateTime = dateTime;
    }

    public LocalDate getDateTime() { return dateTime; }
    public void setDateTime(LocalDate dateTime) { this.dateTime = dateTime; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHallId() { return hallId; }
    public void setHallId(String hallId) { this.hallId = hallId; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public List<StaffAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<StaffAssignment> assignments) { this.assignments = assignments; }


}


