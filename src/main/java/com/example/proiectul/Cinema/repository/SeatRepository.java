package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Seat;
import org.springframework.stereotype.Repository;
import java.nio.file.*;

@Repository
public class SeatRepository extends InFileRepository<Seat> {
    private static final Path DATA = Paths.get("src/main/resources/data/seat.json");
    public SeatRepository() { super(DATA, Seat.class, Seat::getId, Seat::setId); }
}
