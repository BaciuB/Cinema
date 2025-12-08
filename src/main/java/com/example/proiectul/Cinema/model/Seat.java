package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
        name = "seats"
)
public class Seat {

    @Id
    @Column(length = 50)
    @NotBlank(message = "Seat ID is required")
    @Size(max = 50, message = "Seat ID must be at most 50 characters")
    private String id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    @NotNull(message = "Hall is required")
    private Hall hall;

    @Column(name = "number_row", nullable = false)
    @Min(value = 1, message = "Row number must be at least 1")
    @Max(value = 100, message = "Row number must be at most 100")
    private int rowNumber;

    @Column(name = "number_column", nullable = false)
    @Min(value = 1, message = "Column number must be at least 1")
    @Max(value = 100, message = "Column number must be at most 100")
    private int columnNumber;

    public Seat() {
    }

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
}
