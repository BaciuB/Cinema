package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.StaffAssignment;
import org.springframework.stereotype.Repository;
import java.nio.file.*;

@Repository
public class StaffAssignmentRepository extends InFileRepository<StaffAssignment> {
    private static final Path DATA = Paths.get("src/main/resources/data/staffassignment.json");
    public StaffAssignmentRepository() {
        super(DATA, StaffAssignment.class, StaffAssignment::getId, StaffAssignment::setId);
    }
}