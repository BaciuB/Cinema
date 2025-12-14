package com.example.proiectul.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "staff_assignments")
public class StaffAssignment {

    @Id
    @Column(length = 50)
    @NotBlank(message = "ID is required")
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "technical_operator_id")
    private TechnicalOperator technicalOperator;

    @ManyToOne
    @JoinColumn(name = "support_staff_id")
    private SupportStaff supportStaff;

    @Transient
//    @NotBlank(message = "Screening ID is required")
    private String screeningId;

    @Transient
//    @NotBlank(message = "Staff ID is required")
    private String staffId;

    public StaffAssignment() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }

    public TechnicalOperator getTechnicalOperator() { return technicalOperator; }
    public void setTechnicalOperator(TechnicalOperator technicalOperator) { this.technicalOperator = technicalOperator; }

    public SupportStaff getSupportStaff() { return supportStaff; }
    public void setSupportStaff(SupportStaff supportStaff) { this.supportStaff = supportStaff; }

    public String getScreeningId() { return screeningId; }
    public void setScreeningId(String screeningId) { this.screeningId = screeningId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    @Transient
    public String getAssignedStaffId() {
        if (technicalOperator != null) return technicalOperator.getId();
        if (supportStaff != null) return supportStaff.getId();
        return null;
    }
}
