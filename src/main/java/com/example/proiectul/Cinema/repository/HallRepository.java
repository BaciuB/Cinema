package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, String> {

    List<Hall> findByTheatre_Id(String theatre_id);
}
