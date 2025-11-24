package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Seat;
import com.example.proiectul.Cinema.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private final SeatRepository seatRepo;

    public SeatService(SeatRepository seatRepo) {
        this.seatRepo = seatRepo;
    }

    public List<Seat> findAll() { return seatRepo.findAll(); }
    public Optional<Seat> findById(String id) { return seatRepo.findById(id); } // id == seatNumber
    public Seat save(Seat seat) { return seatRepo.save(seat); }
    public void deleteById(String id) { seatRepo.deleteById(id); }

    public List<Seat> findByHallId(String hallId) {
        return seatRepo.findAll().stream()
                .filter(s -> s.getHallid().equals(hallId))  // atenție: getHallid(), nu getHallId()
                .toList();
    }
}