package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository
        extends JpaRepository<Theatre, String>,
        JpaSpecificationExecutor<Theatre> {
}
