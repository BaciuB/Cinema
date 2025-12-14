package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.Role;
import com.example.proiectul.Cinema.model.SupportStaff;
import org.springframework.data.jpa.domain.Specification;

public class SupportStaffSpecs {

    public static Specification<SupportStaff> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<SupportStaff> roleEquals(Role role) {
        return (root, query, cb) -> {
            if (role == null) return cb.conjunction();
            return cb.equal(root.get("role"), role);
        };
    }

    public static Specification<SupportStaff> salaryGte(Integer minSalary) {
        return (root, query, cb) -> {
            if (minSalary == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
        };
    }

    public static Specification<SupportStaff> salaryLte(Integer maxSalary) {
        return (root, query, cb) -> {
            if (maxSalary == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("salary"), maxSalary);
        };
    }
}
