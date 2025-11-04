package com.example.proiectul.Cinema.model;

public class TechnicalOperator extends Staff {
    private String specialization;//sa le convertim in enum

    public TechnicalOperator() { }

    public TechnicalOperator( String Id, String name, int salary, String specialization) {
        super(Id, name, salary);
        this.specialization = specialization;
    }

    public String getSpecialization() {return specialization; }
    public void setSpecialization(String specialization) {this.specialization = specialization;}

}

