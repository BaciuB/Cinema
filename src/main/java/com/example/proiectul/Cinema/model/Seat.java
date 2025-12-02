package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @Column(length = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    @NotNull(message = "Hall is required")
    private Hall hall;

    @Column(name = "number_row", nullable = false)
    @Min(value = 1, message = "Row must be >= 1")
    private int rowNumber;

    @Column(name = "number_column", nullable = false)
    @Min(value = 1, message = "Column must be >= 1")
    private int columnNumber;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public Seat() {}

    public Seat(String id, Hall hall, int rowNumber, int columnNumber) {
        this.id = id;
        this.hall = hall;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }

    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public int getColumnNumber() { return columnNumber; }
    public void setColumnNumber(int columnNumber) { this.columnNumber = columnNumber; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }
}
