package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.SupportStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportStaffRepository extends JpaRepository<SupportStaff, String>, JpaSpecificationExecutor<SupportStaff> {
}
