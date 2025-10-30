package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.StaffAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class StaffAssignmentRepo extends RepoMemory<StaffAssignment>{
    public StaffAssignmentRepo() {
        super(StaffAssignment::getId);
    }
}
