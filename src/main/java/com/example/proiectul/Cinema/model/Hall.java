package com.example.proiectul.Cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Hall {
    private String id;
    private String name;
    private String theatreId;
    private int capacity;
    private List<Seat> seats = new ArrayList<>();
    private List<Screening> screenings = new ArrayList<>();

    public Hall() { }

    public Hall(String id, String name, String theatreId, int capacity) {
        this.id = id;
        this.name = name;
        this.theatreId = theatreId;
        this.capacity = capacity;
    }

    public String getId() {return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTheatreId() { return theatreId; }
    public void setTheatreId(String theatreId) { this.theatreId = theatreId; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }

    public List<Screening> getScreenings() { return screenings; }
    public void setScreenings(List<Screening> screenings) { this.screenings = screenings; }
}

