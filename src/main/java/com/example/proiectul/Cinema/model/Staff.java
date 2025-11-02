package com.example.proiectul.Cinema.model;

public abstract class Staff {
    private String Id;
    private String name;
    private int salary;

    public Staff() { }

    public Staff(String id, String name, int salary) {
        this.Id = id;
        this.name = name;
        this.salary = salary;
    }
    public String getId() {return Id; }
    public void setId(String id) { this.Id = id; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

}
