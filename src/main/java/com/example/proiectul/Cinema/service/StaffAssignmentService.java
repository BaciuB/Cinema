package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.StaffAssignment;
import com.example.proiectul.Cinema.repository.StaffAssignmentRepository;
import com.example.proiectul.Cinema.repository.spec.StaffAssignmentSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffAssignmentService {

    private final StaffAssignmentRepository repo;

    public StaffAssignmentService(StaffAssignmentRepository repo) {
        this.repo = repo;
    }

    public List<StaffAssignment> findAll() {
        return repo.findAll();
    }

    public Optional<StaffAssignment> findById(String id) {
        return repo.findById(id);
    }

    public StaffAssignment save(StaffAssignment a) {
        if (a != null) {
            if (a.getScreening() != null) {
                a.setScreeningId(a.getScreening().getId());
            }
            String assigned = a.getAssignedStaffId();
            if (assigned != null) {
                a.setStaffId(assigned);
            }
        }
        return repo.save(a);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }

    public boolean staffAlreadyAssignedForScreening(String screeningId, String staffId) {
        if (screeningId == null || staffId == null) return false;
        if (staffId.startsWith("TO")) return repo.existsByScreening_IdAndTechnicalOperator_Id(screeningId, staffId);
        if (staffId.startsWith("SST")) return repo.existsByScreening_IdAndSupportStaff_Id(screeningId, staffId);
        return false;
    }

    public boolean staffAlreadyAssignedForScreeningExcept(String assignmentId, String screeningId, String staffId) {
        if (assignmentId == null || screeningId == null || staffId == null) return false;
        if (staffId.startsWith("TO")) return repo.existsByIdNotAndScreening_IdAndTechnicalOperator_Id(assignmentId, screeningId, staffId);
        if (staffId.startsWith("SST")) return repo.existsByIdNotAndScreening_IdAndSupportStaff_Id(assignmentId, screeningId, staffId);
        return false;
    }

    public Page<StaffAssignment> findWithFilters(String staffId, String screeningId, Pageable pageable) {
        Specification<StaffAssignment> spec = Specification
                .allOf(StaffAssignmentSpecs.staffIdEquals(staffId))
                .and(StaffAssignmentSpecs.screeningIdEquals(screeningId));
        return repo.findAll(spec, pageable);
    }
}
