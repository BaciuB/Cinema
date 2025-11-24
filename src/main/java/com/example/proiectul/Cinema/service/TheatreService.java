package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Theatre;
import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepo;
    private final HallService hallService;   // 🔹 nou

    public TheatreService(TheatreRepository theatreRepo, HallService hallService) {
        this.theatreRepo = theatreRepo;
        this.hallService = hallService;
    }

    public List<Theatre> findAll() { return theatreRepo.findAll(); }
    public Optional<Theatre> findById(String id) { return theatreRepo.findById(id); }
    public Theatre save(Theatre theatre) { return theatreRepo.save(theatre); }
    public void deleteById(String id) { theatreRepo.deleteById(id); }

    public Theatre findTheatreWithHalls(String id) {
        Theatre t = theatreRepo.findById(id).orElseThrow();
        t.setHalls(hallService.findByTheatreId(id));
        return t;
    }
}