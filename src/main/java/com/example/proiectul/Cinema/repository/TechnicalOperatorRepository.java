package com.example.proiectul.Cinema.repository;
import com.example.proiectul.Cinema.model.TechnicalOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalOperatorRepository extends JpaRepository<TechnicalOperator, String>, JpaSpecificationExecutor<TechnicalOperator> {
}