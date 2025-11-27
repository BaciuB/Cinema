package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {
    private final HallRepository hallRepo;
    private final SeatService seatService;
    private final ScreeningService screeningService;

    public HallService(HallRepository hallRepository, SeatService seatService, ScreeningService screeningService) {
        this.hallRepo = hallRepository;
        this.seatService = seatService;
        this.screeningService = screeningService;
    }

    public List<Hall> findAll() { return hallRepo.findAll(); }
    public Optional<Hall> findById(String id) { return hallRepo.findById(id); }
    public Hall save(Hall hall) { return hallRepo.save(hall); }
    public void deleteById(String id) { hallRepo.deleteById(id); }

    public Hall findHallWithRelations(String id) {
        Hall h = hallRepo.findById(id).orElseThrow();
        h.setSeats(seatService.findByHallId(id));
        h.setScreenings(screeningService.findByHallId(id));

        return h;
    }

    public List<Hall> findByTheatreId(String theatreId) {
        return hallRepo.findAll().stream()
                .filter(h -> h.getTheatreId().equals(theatreId))
                .toList();
    }
}