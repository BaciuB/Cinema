package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

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
