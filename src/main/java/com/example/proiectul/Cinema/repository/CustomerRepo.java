package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepo extends RepoMemory<Customer> {
    public CustomerRepo() {
        super(Customer::getId);
    }
}
