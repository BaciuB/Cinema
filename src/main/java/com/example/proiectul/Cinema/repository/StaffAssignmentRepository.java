package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.StaffAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.nio.file.*;

@Repository
public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment, String> {
}