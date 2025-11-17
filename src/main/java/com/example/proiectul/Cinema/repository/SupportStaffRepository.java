package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.SupportStaff;
import org.springframework.stereotype.Repository;
import java.nio.file.*;

@Repository
public class SupportStaffRepository extends InFileRepository<SupportStaff> {
    private static final Path DATA = Paths.get("src/main/resources/data/supportstaff.json");
    public SupportStaffRepository() {
        super(DATA, SupportStaff.class, SupportStaff::getId, SupportStaff::setId);
    }
}