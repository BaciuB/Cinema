package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "seats")
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
    @NotNull(message = "Row number is required")
    @Min(value = 1, message = "Row number must be at least 1")
    @Max(value = 100, message = "Row number must be at most 100")
    private Integer rowNumber;   // <- era int

    @Column(name = "number_column", nullable = false)
    @NotNull(message = "Column number is required")
    @Min(value = 1, message = "Column number must be at least 1")
    @Max(value = 100, message = "Column number must be at most 100")
    private Integer columnNumber;   // <- era int

    public Seat() {
    }

    public Seat(String id, Hall hall, Integer rowNumber, Integer columnNumber) {
        this.id = id;
        this.hall = hall;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }

    public Integer getColumnNumber() { return columnNumber; }
    public void setColumnNumber(Integer columnNumber) { this.columnNumber = columnNumber; }
}
