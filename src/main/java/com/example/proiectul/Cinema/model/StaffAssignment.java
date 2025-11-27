package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "staff_assignments")
public class StaffAssignment {
    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "screening_id", nullable = false, length = 50)
    private String screeningId;

    @Column(name = "staff_id", nullable = false, length = 50)
    private String staffId;

    public StaffAssignment() { }

    public StaffAssignment(String id, String screeningId, String staffId) {
        this.id = id;
        this.screeningId = screeningId;
        this.staffId = staffId;
    }

    public String getId() {return id; }
    public void setId(String id) { this.id = id; }

    public String getScreeningId() { return screeningId; }
    public void setScreeningId(String screeningId) { this.screeningId = screeningId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

}
