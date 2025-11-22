package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepo;
    private final ScreeningService screeningService;


    public MovieService(MovieRepository movieRepo, ScreeningService screeningService) {
        this.movieRepo = movieRepo;
        this.screeningService = screeningService;
    }

    public List<Movie> findAll() { return movieRepo.findAll(); }

    public Optional<Movie> findById(String id) { return movieRepo.findById(id); }

    public Movie save(Movie movie) { return movieRepo.save(movie); }

    public void deleteById(String id) { movieRepo.deleteById(id); }

    public Movie findMovieWithScreenings(String id) {
        Movie m = movieRepo.findById(id).orElseThrow();
        List<Screening> sc = screeningService.findByMovieId(id);
        m.setScreenings(sc);
        return m;
    }

}