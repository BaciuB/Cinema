package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String>, JpaSpecificationExecutor<Seat> {

    boolean existsByHall_IdAndRowNumberAndColumnNumber(String hallId, Integer rowNumber, Integer columnNumber);

    boolean existsByIdNotAndHall_IdAndRowNumberAndColumnNumber(String id, String hallId, Integer rowNumber, Integer columnNumber);
}
