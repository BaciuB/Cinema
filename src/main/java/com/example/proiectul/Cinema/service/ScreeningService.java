package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.repository.ScreeningRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {
    private final ScreeningRepo screeningRepo;

    public ScreeningService(ScreeningRepo screeningRepo) {
        this.screeningRepo = screeningRepo;
    }

    public List<Screening> findAll() { return screeningRepo.findAll(); }
    public Optional<Screening> findById(String id) { return screeningRepo.findById(id); }
    public Screening save(Screening screening) { return screeningRepo.save(screening); }
    public void deleteById(String id) { screeningRepo.deleteById(id); }
}