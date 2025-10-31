package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Theatre;
import com.example.proiectul.Cinema.repository.TheatreRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    private final TheatreRepo theatreRepo;

    public TheatreService(TheatreRepo theatreRepo) {
        this.theatreRepo = theatreRepo;
    }

    public List<Theatre> findAll() { return theatreRepo.findAll(); }
    public Optional<Theatre> findById(String id) { return theatreRepo.findById(id); }
    public Theatre save(Theatre theatre) { return theatreRepo.save(theatre); }
    public void deleteById(String id) { theatreRepo.deleteById(id); }
}