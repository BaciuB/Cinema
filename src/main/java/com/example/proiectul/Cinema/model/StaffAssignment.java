package com.example.proiectul.Cinema.model;

public class StaffAssignment {
    private String Id;
    private String screeningId;
    private String staffId;

    public StaffAssignment() { }

    public StaffAssignment(String Id, String screeningId, String staffId) {
        this.Id = Id;
        this.screeningId = screeningId;
        this.staffId = staffId;
    }

    public String getId() {return Id; }
    public void setId(String Id) { this.Id = Id; }

    public String getScreeningId() { return screeningId; }
    public void setScreeningId(String screeningId) { this.screeningId = screeningId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

}
