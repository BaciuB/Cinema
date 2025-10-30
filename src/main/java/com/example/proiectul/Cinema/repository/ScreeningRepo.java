package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Screening;
import org.springframework.stereotype.Repository;

@Repository
public class ScreeningRepo extends RepoMemory<Screening>{
    public ScreeningRepo() {
        super(Screening::getId);
    }
}
