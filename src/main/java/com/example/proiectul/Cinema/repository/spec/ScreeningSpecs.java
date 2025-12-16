package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Screening;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ScreeningSpecs {

    public static Specification<Screening> hallIdEquals(String hallId) {
        return (root, query, cb) -> {
            if (hallId == null || hallId.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("hall").get("id"), hallId.trim());
        };
    }

    public static Specification<Screening> movieIdEquals(String movieId) {
        return (root, query, cb) -> {
            if (movieId == null || movieId.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("movie").get("id"), movieId.trim());
        };
    }

    public static Specification<Screening> dateFrom(LocalDate from) {
        return (root, query, cb) -> {
            if (from == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("dateTime"), from);
        };
    }

    public static Specification<Screening> dateTo(LocalDate to) {
        return (root, query, cb) -> {
            if (to == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("dateTime"), to);
        };
    }
}
