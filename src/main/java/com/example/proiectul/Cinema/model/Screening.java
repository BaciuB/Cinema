package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "screenings")
public class Screening {

    @Id
    @Column(length = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "date_time", nullable = false)
    private LocalDate dateTime;

    @Transient
    private List<Ticket> tickets = new ArrayList<>();

    @Transient
    private List<StaffAssignment> assignments = new ArrayList<>();

    public Screening() {
    }

    public Screening(String id, Hall hall, Movie movie, LocalDate dateTime) {
        this.id = id;
        this.hall = hall;
        this.movie = movie;
        this.dateTime = dateTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public LocalDate getDateTime() { return dateTime; }
    public void setDateTime(LocalDate dateTime) { this.dateTime = dateTime; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public List<StaffAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<StaffAssignment> assignments) { this.assignments = assignments; }
}
