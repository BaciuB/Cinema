package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Movie;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class MovieRepository extends InFileRepository<Movie> {
    private static final Path DATA = Paths.get("src/main/resources/data/movie.json");

    public MovieRepository() {
        super(DATA, Movie.class, Movie::getId, Movie::setId);
    }
}

