package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.StaffAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment, String>, JpaSpecificationExecutor<StaffAssignment> {

    boolean existsByScreening_IdAndTechnicalOperator_Id(String screeningId, String staffId);
    boolean existsByScreening_IdAndSupportStaff_Id(String screeningId, String staffId);

    boolean existsByIdNotAndScreening_IdAndTechnicalOperator_Id(String assignmentId, String screeningId, String staffId);
    boolean existsByIdNotAndScreening_IdAndSupportStaff_Id(String assignmentId, String screeningId, String staffId);
}
