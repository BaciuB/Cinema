package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.repository.MovieRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepo movieRepo;

    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<Movie> findAll() { return movieRepo.findAll(); }

    public Optional<Movie> findById(String id) { return movieRepo.findById(id); }

    public Movie save(Movie movie) { return movieRepo.save(movie); }

    public void deleteById(String id) { movieRepo.deleteById(id); }
}