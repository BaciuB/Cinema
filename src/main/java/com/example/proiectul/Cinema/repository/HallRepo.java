package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Hall;
import org.springframework.stereotype.Repository;

@Repository
public class HallRepo extends RepoMemory<Hall> {
    public HallRepo() {
        super(Hall::getId);
    }

}
