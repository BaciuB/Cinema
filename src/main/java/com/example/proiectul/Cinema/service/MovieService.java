package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.repository.MovieRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepo;

    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    /* ===== EXISTENTE ===== */

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

    /* ===== NOU – ITERATION 5 ===== */

    public List<Movie> findFilteredAndSorted(
            String title,
            Integer minDuration,
            Integer maxDuration,
            LocalDate releaseFrom,
            LocalDate releaseTo,
            String sortField,
            String sortDir
    ) {
        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        String t = (title == null || title.isBlank()) ? null : title;

        return movieRepo.filterMovies(
                t,
                minDuration,
                maxDuration,
                releaseFrom,
                releaseTo,
                sort
        );
    }
}
