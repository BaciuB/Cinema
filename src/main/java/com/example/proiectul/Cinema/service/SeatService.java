package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Seat;
import com.example.proiectul.Cinema.repository.SeatRepository;
import com.example.proiectul.Cinema.repository.spec.SeatSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository repo;

    public SeatService(SeatRepository repo) {
        this.repo = repo;
    }

    public List<Seat> findAll() {
        return repo.findAll();
    }

    public Page<Seat> findWithFilters(String hallId, Integer rowNumber, Integer columnNumber, Pageable pageable) {
        Specification<Seat> spec = SeatSpecs.hallIdEquals(hallId)
                .and(SeatSpecs.rowEquals(rowNumber))
                .and(SeatSpecs.colEquals(columnNumber));
        return repo.findAll(spec, pageable);
    }

    public Optional<Seat> findById(String id) {
        return repo.findById(id);
    }

    public Seat save(Seat seat) {
        return repo.save(seat);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }

    public boolean seatExistsInHall(String hallId, Integer rowNumber, Integer columnNumber) {
        return repo.existsByHall_IdAndRowNumberAndColumnNumber(hallId, rowNumber, columnNumber);
    }

    public boolean seatExistsInHallExcept(String id, String hallId, Integer rowNumber, Integer columnNumber) {
        return repo.existsByIdNotAndHall_IdAndRowNumberAndColumnNumber(id, hallId, rowNumber, columnNumber);
    }
}
