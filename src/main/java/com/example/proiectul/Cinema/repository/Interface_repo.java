package com.example.proiectul.Cinema.repository;
import java.util.List;
import java.util.Optional;

public interface Interface_repo<T, String> {
    Optional<T> findById(String id);
    List<T> findAll();
    T save(T entity);
    void deleteById(String id);
}
