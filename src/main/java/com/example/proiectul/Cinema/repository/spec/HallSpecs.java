package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Hall;
import org.springframework.data.jpa.domain.Specification;

public class HallSpecs {

    public static Specification<Hall> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Hall> capacityGte(Integer minCapacity) {
        return (root, query, cb) -> {
            if (minCapacity == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("capacity"), minCapacity);
        };
    }

    public static Specification<Hall> capacityLte(Integer maxCapacity) {
        return (root, query, cb) -> {
            if (maxCapacity == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("capacity"), maxCapacity);
        };
    }

    public static Specification<Hall> theatreIdEquals(String theatreId) {
        return (root, query, cb) -> {
            if (theatreId == null || theatreId.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("theatre").get("id"), theatreId.trim());
        };
    }
}
