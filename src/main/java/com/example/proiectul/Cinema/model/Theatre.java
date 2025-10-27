package com.example.proiectul.Cinema.model;

import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private String location;
    private List<Seat> seats;

    public Theatre() { }

    public Theatre(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
}
