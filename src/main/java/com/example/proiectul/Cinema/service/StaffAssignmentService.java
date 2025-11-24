package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.StaffAssignment;
import com.example.proiectul.Cinema.repository.StaffAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepository repo;

    public StaffAssignmentService(StaffAssignmentRepository repo) {
        this.repo = repo;
    }

    public List<StaffAssignment> findAll() { return repo.findAll(); }
    public Optional<StaffAssignment> findById(String id) { return repo.findById(id); }
    public StaffAssignment save(StaffAssignment assignment) { return repo.save(assignment); }
    public void deleteById(String id) { repo.deleteById(id); }

    public List<StaffAssignment> findByScreeningId(String screeningId) {
        return repo.findAll().stream()
                .filter(sa -> sa.getScreeningId().equals(screeningId))
                .toList();
    }
}