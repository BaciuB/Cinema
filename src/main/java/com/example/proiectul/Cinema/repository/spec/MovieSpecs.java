package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Movie;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MovieSpecs {

    public static Specification<Movie> titleContains(String title) {
        return (root, query, cb) -> {
            if (title == null || title.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("title")), "%" + title.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Movie> durationGte(Integer minDuration) {
        return (root, query, cb) -> {
            if (minDuration == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("durationMinutes"), minDuration);
        };
    }

    public static Specification<Movie> durationLte(Integer maxDuration) {
        return (root, query, cb) -> {
            if (maxDuration == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("durationMinutes"), maxDuration);
        };
    }

    public static Specification<Movie> releaseFrom(LocalDate from) {
        return (root, query, cb) -> {
            if (from == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("release_date"), from);
        };
    }

    public static Specification<Movie> releaseTo(LocalDate to) {
        return (root, query, cb) -> {
            if (to == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("release_date"), to);
        };
    }
}
