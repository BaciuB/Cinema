// src/main/java/com/example/proiectul/Cinema/repository/spec/TicketSpecs.java
package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Ticket;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecs {

    public static Specification<Ticket> screeningIdEquals(String screeningId) {
        return (root, query, cb) -> {
            if (screeningId == null || screeningId.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("screening").get("id"), screeningId.trim());
        };
    }

    public static Specification<Ticket> seatIdEquals(String seatId) {
        return (root, query, cb) -> {
            if (seatId == null || seatId.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("seat").get("id"), seatId.trim());
        };
    }

    public static Specification<Ticket> customerNameContains(String customerName) {
        return (root, query, cb) -> {
            if (customerName == null || customerName.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("customer").get("name")), "%" + customerName.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Ticket> priceGte(Double minPrice) {
        return (root, query, cb) -> {
            if (minPrice == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    public static Specification<Ticket> priceLte(Double maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
