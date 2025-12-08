package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required")
    @Size(max = 50, message = "ID must be at most 50 characters")
    private String id;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 250, message = "Capacity must be at most 250")
    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Screening> screenings = new ArrayList<>();

    public Hall() {}

    public Hall(String id, Theatre theatre, String name, int capacity) {
        this.setId(id);
        this.theatre = theatre;
        this.setName(name);
        this.capacity = capacity;
    }

    public String getId() { return id; }
    public void setId(String id) {
        this.id = (id != null ? id.trim() : null);
    }

    public Theatre getTheatre() { return theatre; }
    public void setTheatre(Theatre theatre) { this.theatre = theatre; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = (name != null ? name.trim() : null);
    }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }

    public List<Screening> getScreenings() { return screenings; }
    public void setScreenings(List<Screening> screenings) { this.screenings = screenings; }
}
