package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepo extends RepoMemory<Movie> {
    public MovieRepo() {
        super(Movie::getId);
    }
}
