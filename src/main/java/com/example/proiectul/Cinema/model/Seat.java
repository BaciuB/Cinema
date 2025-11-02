package com.example.proiectul.Cinema.model;

public class Seat {
    private String id;
    private  String hallid;
    private String row;
    private String column;

    public Seat() { }

    public Seat(String id, String hallid, String row, String column) {
        this.id = id;
        this.hallid = hallid;
        this.row = row;
        this.column = column;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHallid() { return hallid; }
    public void setHallid(String hallid) { this.hallid = hallid;}

    public String getRow() { return row; }
    public void setRow(String row) { this.row = row; }

    public String getColumn() { return column; }
    public void setColumn(String column) { this.column = column; }

}
