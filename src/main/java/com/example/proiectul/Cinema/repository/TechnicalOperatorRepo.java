package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.TechnicalOperator;
import org.springframework.stereotype.Repository;

@Repository
public class TechnicalOperatorRepo extends  RepoMemory<TechnicalOperator>{
    public TechnicalOperatorRepo() {
        super(TechnicalOperator::getId);
    }
}
