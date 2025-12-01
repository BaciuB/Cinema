package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @Column(length = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(nullable = false)
    private int rowNumber;

    @Column(nullable = false)
    private int seatNumber;

    public Seat() {
    }

    public Seat(String id, Hall hall, int rowNumber, int seatNumber) {
        this.id = id;
        this.hall = hall;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }

    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
}
