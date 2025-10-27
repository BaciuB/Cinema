package com.example.proiectul.Cinema.model;

public class Ticket {
    private String id;
    private Movie movie;
    private Seat seat;
    private Theatre theatre;
    private Customer customer;

    public Ticket() { }

    public Ticket(String id, Movie movie, Seat seat, Theatre theatre, Customer customer) {
        this.id = id;
        this.movie = movie;
        this.seat = seat;
        this.theatre = theatre;
        this.customer = customer;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public Theatre getTheatre() { return theatre; }
    public void setTheatre(Theatre theater) { this.theatre = theatre; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}
