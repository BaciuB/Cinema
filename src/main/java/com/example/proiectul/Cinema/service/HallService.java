package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.repository.HallRepository;
import com.example.proiectul.Cinema.repository.spec.HallSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {

    private final HallRepository hallRepo;

    public HallService(HallRepository hallRepo) {
        this.hallRepo = hallRepo;
    }

    public List<Hall> findAll() {
        return hallRepo.findAll();
    }

    public Page<Hall> findWithFilters(String name, Integer minCapacity, Integer maxCapacity, String theatreId, Pageable pageable) {
        Specification<Hall> spec = Specification
                .allOf(HallSpecs.nameContains(name))
                .and(HallSpecs.capacityGte(minCapacity))
                .and(HallSpecs.capacityLte(maxCapacity))
                .and(HallSpecs.theatreIdEquals(theatreId));
        return hallRepo.findAll(spec, pageable);
    }

    public Optional<Hall> findById(String id) {
        return hallRepo.findById(id);
    }

    public Hall save(Hall hall) {
        return hallRepo.save(hall);
    }

    public void deleteById(String id) {
        hallRepo.deleteById(id);
    }

    public Hall findHallWithRelations(String id) {
        return hallRepo.findById(id).orElse(null);
    }

    public List<Hall> findByTheatreId(String theatreId) {
        return hallRepo.findByTheatre_Id(theatreId);
    }
}
