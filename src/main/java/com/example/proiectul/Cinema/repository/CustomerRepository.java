package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, String>,
        JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByEmail(String email);
}
