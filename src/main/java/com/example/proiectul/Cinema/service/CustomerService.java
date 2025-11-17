package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Customer;
import com.example.proiectul.Cinema.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;

    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> findAll() { return customerRepo.findAll(); }
    public Optional<Customer> findById(String id) { return customerRepo.findById(id); }
    public Customer save(Customer customer) { return customerRepo.save(customer); }
    public void deleteById(String id) { customerRepo.deleteById(id); }
}