package com.example.proiectul.Cinema.model;

public class Seat {
    private String seatNumber;
    private boolean available;
    private double price;

    public Seat() { }

    public Seat(String seatNumber, boolean available, double price) {
        this.seatNumber = seatNumber;
        this.available = available;
        this.price = price;
    }

    // Getters and Setters
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
