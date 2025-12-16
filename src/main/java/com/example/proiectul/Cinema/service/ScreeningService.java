package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.repository.ScreeningRepository;
import com.example.proiectul.Cinema.repository.spec.ScreeningSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    private final ScreeningRepository repo;

    public ScreeningService(ScreeningRepository repo) {
        this.repo = repo;
    }

    public List<Screening> findAll() {
        return repo.findAll();
    }

    public Page<Screening> findWithFilters(String hallId, String movieId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        Specification<Screening> spec = ScreeningSpecs.hallIdEquals(hallId)
                .and(ScreeningSpecs.movieIdEquals(movieId))
                .and(ScreeningSpecs.dateFrom(dateFrom))
                .and(ScreeningSpecs.dateTo(dateTo));
        return repo.findAll(spec, pageable);
    }

    public Optional<Screening> findById(String id) {
        return repo.findById(id);
    }

    public Screening save(Screening screening) {
        return repo.save(screening);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }

    public List<Screening> findByHallId(String hallId) {
        return repo.findByHall_Id(hallId);
    }

    public List<Screening> findByMovieId(String movieId) {
        return repo.findByMovie_Id(movieId);
    }
}
