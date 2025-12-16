package com.example.proiectul.Cinema.repository.spec;

import com.example.proiectul.Cinema.model.StaffAssignment;
import org.springframework.data.jpa.domain.Specification;

public class StaffAssignmentSpecs {

    public static Specification<StaffAssignment> screeningIdEquals(String screeningId) {
        return (root, query, cb) -> {
            if (screeningId == null || screeningId.trim().isEmpty()) return cb.conjunction();
            return cb.equal(root.get("screening").get("id"), screeningId.trim());
        };
    }

    public static Specification<StaffAssignment> staffIdEquals(String staffId) {
        return (root, query, cb) -> {
            if (staffId == null || staffId.trim().isEmpty()) return cb.conjunction();

            String v = staffId.trim();
            var toJoin = root.join("technicalOperator", jakarta.persistence.criteria.JoinType.LEFT);
            var ssJoin = root.join("supportStaff", jakarta.persistence.criteria.JoinType.LEFT);

            var toPred = cb.equal(toJoin.get("id"), v);
            var ssPred = cb.equal(ssJoin.get("id"), v);

            return cb.or(toPred, ssPred);
        };
    }
}
