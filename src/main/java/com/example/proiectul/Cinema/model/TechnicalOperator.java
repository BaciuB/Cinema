package com.example.proiectul.Cinema.model;

public class TechnicalOperator extends Staff {
    private Specialization specialization;

    public TechnicalOperator() { }

    public TechnicalOperator( String Id, String name, int salary, Specialization specialization) {
        super(Id, name, salary);
        this.specialization = specialization;
    }

    public Specialization getSpecialization() {return specialization; }
    public void setSpecialization(Specialization specialization) {this.specialization = specialization;}

}

