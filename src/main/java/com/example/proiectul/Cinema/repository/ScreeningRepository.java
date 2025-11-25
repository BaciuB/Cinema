package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, String> {
}
