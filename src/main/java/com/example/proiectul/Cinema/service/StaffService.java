package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Staff;
import com.example.proiectul.Cinema.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository repo;

    public StaffService(StaffRepository repo) {
        this.repo = repo;
    }

    public List<Staff> findAll() {
        return repo.findAll();
    }

    public Optional<Staff> findById(String id) {
        return repo.findById(id);
    }
}
