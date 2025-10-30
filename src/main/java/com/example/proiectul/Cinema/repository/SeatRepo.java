package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Seat;
import org.springframework.stereotype.Repository;

@Repository
public class SeatRepo extends RepoMemory<Seat>{
    public SeatRepo() {
        super(Seat::getSeatNumber);
    }
}
