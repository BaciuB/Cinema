package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.repository.ScreeningRepository;
import com.example.proiectul.Cinema.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    private final ScreeningRepository repo;
    private final MovieService movieService;

    public ScreeningService(ScreeningRepository repo, MovieService movieService) {
        this.repo = repo;
        this.movieService = movieService;
    }

    public List<Screening> findAll() {
        return repo.findAll();
    }

    public Optional<Screening> findById(String id) {
        return repo.findById(id);
    }

    public List<Screening> findByMovieId(String movieId) {
        return repo.findAll().stream()
                .filter(s -> s.getMovieId().equals(movieId))
                .toList();
    }

    public Screening save(Screening screening) {
        movieService.findById(screening.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie does not exist"));
        return repo.save(screening);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }
}
