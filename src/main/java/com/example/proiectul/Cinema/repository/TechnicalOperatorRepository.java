package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.TechnicalOperator;
import org.springframework.stereotype.Repository;
import java.nio.file.*;

@Repository
public class TechnicalOperatorRepository extends  InFileRepository<TechnicalOperator>{
    private static final Path DATA = Paths.get("src/main/resources/data/technical_operators.json");
    public TechnicalOperatorRepository() {
        super(DATA, TechnicalOperator.class, TechnicalOperator::getId, TechnicalOperator::setId);
    }
}
