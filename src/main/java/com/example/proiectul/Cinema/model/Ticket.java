package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required")
    private String id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @NotNull(message = "Screening is required")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    @NotNull(message = "Seat is required")
    private Seat seat;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    public Ticket() {}

    public Ticket(String id, Screening screening, Customer customer, Seat seat, Double price) {
        this.id = id;
        this.screening = screening;
        this.customer = customer;
        this.seat = seat;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
