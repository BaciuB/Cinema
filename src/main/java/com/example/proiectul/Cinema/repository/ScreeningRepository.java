package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, String> {

    List<Screening> findByHall_Id(String hallId);

    List<Screening> findByMovie_Id(String movieId);
}
