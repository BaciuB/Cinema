package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Theatre;
import org.springframework.data.jpa.domain.Specification;

public class TheatreSpecs {

    public static Specification<Theatre> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Theatre> cityContains(String city) {
        return (root, query, cb) -> {
            if (city == null || city.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("city")), "%" + city.trim().toLowerCase() + "%");
        };
    }
}
