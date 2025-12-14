package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Role;
import com.example.proiectul.Cinema.model.SupportStaff;
import com.example.proiectul.Cinema.repository.SupportStaffRepository;
import com.example.proiectul.Cinema.repository.spec.SupportStaffSpecs;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportStaffService {
    private final SupportStaffRepository repo;

    public SupportStaffService(SupportStaffRepository repo) {
        this.repo = repo;
    }

    public List<SupportStaff> findAll() { return repo.findAll(); }
    public Optional<SupportStaff> findById(String id) { return repo.findById(id); }
    public SupportStaff save(SupportStaff staff) { return repo.save(staff); }
    public void deleteById(String id) { repo.deleteById(id); }

    public Page<SupportStaff> search(String name,
                                     Role role,
                                     Integer minSalary,
                                     Integer maxSalary,
                                     String sortField,
                                     String sortDir,
                                     int page,
                                     int size) {

        Specification<SupportStaff> spec = Specification.allOf(
                SupportStaffSpecs.nameContains(name),
                SupportStaffSpecs.roleEquals(role),
                SupportStaffSpecs.salaryGte(minSalary),
                SupportStaffSpecs.salaryLte(maxSalary)
        );

        if (!("id".equals(sortField) || "name".equals(sortField) || "salary".equals(sortField) || "role".equals(sortField))) {
            sortField = "id";
        }

        Sort.Direction dir = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by(dir, sortField));

        return repo.findAll(spec, pageable);
    }
}
