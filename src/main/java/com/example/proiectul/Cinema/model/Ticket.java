package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "screening_id", nullable = false, length = 50)
    private String screeningId;

    @Column(name = "customer_id", nullable = false, length = 50)
    private String customerId;

    @Column(name = "seat_id", nullable = false, length = 50)
    private String seatId;

    @Column(nullable = false)
    private double price;

    public Ticket() { }

    public Ticket(String id, String screeningId, String customerId, String seatId, double price) {
        this.id = id;
        this.screeningId = screeningId;
        this.customerId = customerId;
        this.seatId = seatId;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getScreeningId() { return screeningId; }
    public void setScreeningId(String screeningId) { this.screeningId = screeningId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getSeatId() { return seatId; }
    public void setSeatId(String seatId) { this.seatId = seatId;}

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
