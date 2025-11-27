package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, String> {
}
