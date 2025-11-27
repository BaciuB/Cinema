package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
@Inheritance(strategy = InheritanceType.JOINED)
//Poate trebe DiscriminatorColumn dar nu sunt sigur (uita-te la seminarul 8)
public abstract class Staff {

    @Id
    @Column(length = 50)
    private String Id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
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
