package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.SupportStaff;
import com.example.proiectul.Cinema.repository.SupportStaffRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportStaffService {
    private final SupportStaffRepo repo;

    public SupportStaffService(SupportStaffRepo repo) {
        this.repo = repo;
    }

    public List<SupportStaff> findAll() { return repo.findAll(); }
    public Optional<SupportStaff> findById(String id) { return repo.findById(id); }
    public SupportStaff save(SupportStaff staff) { return repo.save(staff); }
    public void deleteById(String id) { repo.deleteById(id); }
}