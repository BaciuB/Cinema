package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Screening;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class ScreeningRepository extends InFileRepository<Screening> {
    private static final Path DATA = Paths.get("src/main/resources/data/screening.json");
    public ScreeningRepository() {
        super(DATA, Screening.class, Screening::getId, Screening::setId);
    }
}
