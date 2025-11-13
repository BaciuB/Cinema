package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Hall;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class HallRepository extends InFileRepository<Hall> {
    private static final Path DATA = Paths.get("src/main/resources/data/hall.json");

    public HallRepository() {

        super(DATA, Hall.class, Hall::getId, Hall::setId);
    }

}
