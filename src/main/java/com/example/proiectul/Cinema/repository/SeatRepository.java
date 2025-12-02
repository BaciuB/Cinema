package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

    List<Seat> findByHall_Id(String hallId);
}
