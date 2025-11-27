package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.SupportStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.nio.file.*;

@Repository
public interface SupportStaffRepository extends JpaRepository<SupportStaff, String> {
}