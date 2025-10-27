package com.example.proiectul.Cinema.model;

public class TechnicalOperator extends Staff {
    private String specialization;

    public TechnicalOperator() { }

    public TechnicalOperator( String Id, String name, String specialization) {
        super(Id, name);
        this.specialization = specialization;
    }

    public String getspecialization() {return specialization; }
    public void setspecialization(String specialization) {this.specialization = specialization;}

    @Override
    public String toString() {
        return super.toString() + ", specialization = '" + specialization +"'";
    }
}

