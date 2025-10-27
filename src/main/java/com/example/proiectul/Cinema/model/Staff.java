package com.example.proiectul.Cinema.model;

public abstract class Staff {
    private String Id;
    private String name;

    public Staff() { }

    public Staff(String id, String name) {
        this.Id = id;
        this.name = name;
    }
    public String getId() {return Id; }
    public void setId(String id) { this.Id = id; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Staff{" +
                "id = " + Id + '\'' +
                ", name = '" + name + '\'' +
                '}';
    }
}
