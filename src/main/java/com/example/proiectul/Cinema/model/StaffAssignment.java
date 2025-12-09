package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "staff_assignments")
public class StaffAssignment {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required")
    private String id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @NotNull(message = "Screening is required")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    @NotNull(message = "Staff is required")
    private Staff staff;

    @Transient
    @NotBlank(message = "Screening ID is required")
    private String screeningId;

    @Transient
    @NotBlank(message = "Staff ID is required")
    private String staffId;

    public StaffAssignment() {}

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

    public String getScreeningId() { return screeningId; }
    public void setScreeningId(String screeningId) { this.screeningId = screeningId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
}
