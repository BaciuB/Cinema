package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Theatre;
import org.springframework.stereotype.Repository;

@Repository
public class TheatreRepo extends RepoMemory<Theatre> {
    public TheatreRepo() {
        super(Theatre::getId);
    }
}
