package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepo;

    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<Movie> findAll() {
        return movieRepo.findAll();
    }

    public Optional<Movie> findById(String id) {
        return movieRepo.findById(id);
    }

    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }

    public void deleteById(String id) {
        movieRepo.deleteById(id);
    }

    public boolean existsByTitle(String title) {
        return movieRepo.existsByTitle(title);
    }

    public boolean existsAnotherWithTitle(String id, String title) {
        return movieRepo.existsByTitleAndIdNot(title, id);
    }
}


//pe backend sa facem si validari pentru a verifica daca exista un anumit seat sau asa mai departe