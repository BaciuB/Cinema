package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "staff_assignments")
public class StaffAssignment {

    @Id
    @Column(length = 50)
    private String id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @NotNull(message = "Screening is required")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    @NotNull(message = "Staff is required")
    private Staff staff;

    public StaffAssignment() {
    }

    public StaffAssignment(String id, Screening screening, Staff staff) {
        this.id = id;
        this.screening = screening;
        this.staff = staff;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }

    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
}
