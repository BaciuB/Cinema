package com.example.proiectul.Cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private String city;
    private List<Hall> halls = new ArrayList<>();

    public Theatre() { }

    public Theatre(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.city = location;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<Hall> getHalls() { return halls; }
    public void setHalls(List<Hall> halls) { this.halls = halls; }
}
