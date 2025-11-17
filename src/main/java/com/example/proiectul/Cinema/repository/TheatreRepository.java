package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Theatre;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class TheatreRepository extends InFileRepository<Theatre> {
    private static final Path DATA = Paths.get("src/main/resources/data/theatre.json");
    public TheatreRepository() {
        super(DATA, Theatre.class, Theatre::getId, Theatre::setId);
    }
}
