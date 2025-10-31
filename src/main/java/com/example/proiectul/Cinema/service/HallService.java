package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.repository.HallRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {
    private final HallRepo hallRepo;

    public HallService(HallRepo hallRepo) {
        this.hallRepo = hallRepo;
    }

    public List<Hall> findAll() { return hallRepo.findAll(); }
    public Optional<Hall> findById(String id) { return hallRepo.findById(id); }
    public Hall save(Hall hall) { return hallRepo.save(hall); }
    public void deleteById(String id) { hallRepo.deleteById(id); }
}