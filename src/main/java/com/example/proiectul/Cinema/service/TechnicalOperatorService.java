package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.TechnicalOperator;
import com.example.proiectul.Cinema.repository.TechnicalOperatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalOperatorService {
    private final TechnicalOperatorRepository repo;

    public TechnicalOperatorService(TechnicalOperatorRepository repo) {
        this.repo = repo;
    }

    public List<TechnicalOperator> findAll() { return repo.findAll(); }
    public Optional<TechnicalOperator> findById(String id) { return repo.findById(id); }
    public TechnicalOperator save(TechnicalOperator operator) { return repo.save(operator); }
    public void deleteById(String id) { repo.deleteById(id); }
}