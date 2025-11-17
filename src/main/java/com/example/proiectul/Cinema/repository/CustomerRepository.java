package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Customer;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class CustomerRepository extends InFileRepository<Customer> {
    private static final Path DATA = Paths.get("src/main/resources/data/customer.json");

    public CustomerRepository() {
        super(DATA, Customer.class, Customer::getId, Customer::setId);
    }
}
