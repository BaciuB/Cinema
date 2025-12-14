package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.StaffAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment, String> {

    boolean existsByScreening_IdAndTechnicalOperator_Id(String screeningId, String technicalOperatorId);

    boolean existsByScreening_IdAndSupportStaff_Id(String screeningId, String supportStaffId);
}
