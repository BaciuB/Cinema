package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.repository.MovieRepository;
import com.example.proiectul.Cinema.repository.spec.MovieSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repo;

    public MovieService(MovieRepository repo) {
        this.repo = repo;
    }

    public List<Movie> findAll() {
        return repo.findAll();
    }

    public Optional<Movie> findById(String id) {
        return repo.findById(id);
    }

    public Movie save(Movie movie) {
        return repo.save(movie);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }

    public Page<Movie> findWithFilters(String title,
                                       Integer minDuration,
                                       Integer maxDuration,
                                       LocalDate releaseFrom,
                                       LocalDate releaseTo,
                                       Pageable pageable) {

        Specification<Movie> spec = Specification
                .allOf(MovieSpecs.titleContains(title))
                .and(MovieSpecs.durationGte(minDuration))
                .and(MovieSpecs.durationLte(maxDuration))
                .and(MovieSpecs.releaseFrom(releaseFrom))
                .and(MovieSpecs.releaseTo(releaseTo));

        return repo.findAll(spec, pageable);
    }

    public boolean existsByTitle(String title) {
        if (title == null) return false;
        return repo.count(MovieSpecs.titleContains(title)
                .and((root, q, cb) -> cb.equal(cb.lower(root.get("title")), title.trim().toLowerCase()))) > 0;
    }

    public boolean existsAnotherWithTitle(String id, String title) {
        if (title == null) return false;
        Specification<Movie> spec = (root, q, cb) -> cb.and(
                cb.notEqual(root.get("id"), id),
                cb.equal(cb.lower(root.get("title")), title.trim().toLowerCase())
        );
        return repo.count(spec) > 0;
    }
}
