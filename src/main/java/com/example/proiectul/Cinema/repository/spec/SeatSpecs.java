package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Seat;
import org.springframework.data.jpa.domain.Specification;

public class SeatSpecs {

    public static Specification<Seat> hallIdEquals(String hallId) {
        return (root, query, cb) -> {
            if (hallId == null || hallId.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("hall").get("id"), hallId.trim());
        };
    }

    public static Specification<Seat> rowEquals(Integer row) {
        return (root, query, cb) -> {
            if (row == null) return cb.conjunction();
            return cb.equal(root.get("rowNumber"), row);
        };
    }

    public static Specification<Seat> colEquals(Integer col) {
        return (root, query, cb) -> {
            if (col == null) return cb.conjunction();
            return cb.equal(root.get("columnNumber"), col);
        };
    }
}
