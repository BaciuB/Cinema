package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.SupportStaff;
import org.springframework.stereotype.Repository;

@Repository
public class SupportStaffRepo extends RepoMemory<SupportStaff> {
    public SupportStaffRepo() {
        super(SupportStaff::getId);
    }
}
