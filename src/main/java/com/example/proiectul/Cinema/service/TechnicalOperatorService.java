package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Specialization;
import com.example.proiectul.Cinema.model.TechnicalOperator;
import com.example.proiectul.Cinema.repository.TechnicalOperatorRepository;
import com.example.proiectul.Cinema.repository.spec.TechnicalOperatorSpecs;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalOperatorService {
    private final TechnicalOperatorRepository repo;

    public TechnicalOperatorService(TechnicalOperatorRepository repo) {
        this.repo = repo;
    }

    public List<TechnicalOperator> findAll() { return repo.findAll(); }
    public Optional<TechnicalOperator> findById(String id) { return repo.findById(id); }
    public TechnicalOperator save(TechnicalOperator operator) { return repo.save(operator); }
    public void deleteById(String id) { repo.deleteById(id); }

    public Page<TechnicalOperator> search(String name,
                                          Specialization specialization,
                                          Integer minSalary,
                                          Integer maxSalary,
                                          String sortField,
                                          String sortDir,
                                          int page,
                                          int size) {

        Specification<TechnicalOperator> spec = Specification
                .allOf(TechnicalOperatorSpecs.nameContains(name))
                .and(TechnicalOperatorSpecs.specializationEquals(specialization))
                .and(TechnicalOperatorSpecs.salaryGte(minSalary))
                .and(TechnicalOperatorSpecs.salaryLte(maxSalary));

        if (!("id".equals(sortField) || "name".equals(sortField) || "salary".equals(sortField) || "specialization".equals(sortField))) {
            sortField = "id";
        }

        Sort.Direction dir = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by(dir, sortField));

        return repo.findAll(spec, pageable);
    }
}
