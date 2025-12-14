package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Specialization;
import com.example.proiectul.Cinema.model.TechnicalOperator;
import org.springframework.data.jpa.domain.Specification;

public class TechnicalOperatorSpecs {

    public static Specification<TechnicalOperator> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<TechnicalOperator> specializationEquals(Specialization spec) {
        return (root, query, cb) -> {
            if (spec == null) return cb.conjunction();
            return cb.equal(root.get("specialization"), spec);
        };
    }

    public static Specification<TechnicalOperator> salaryGte(Integer minSalary) {
        return (root, query, cb) -> {
            if (minSalary == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
        };
    }

    public static Specification<TechnicalOperator> salaryLte(Integer maxSalary) {
        return (root, query, cb) -> {
            if (maxSalary == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("salary"), maxSalary);
        };
    }
}
