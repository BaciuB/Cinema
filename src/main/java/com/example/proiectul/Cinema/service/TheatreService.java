package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Theatre;
import com.example.proiectul.Cinema.repository.TheatreRepository;
import com.example.proiectul.Cinema.repository.spec.TheatreSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {

    private final TheatreRepository theatreRepo;

    public TheatreService(TheatreRepository theatreRepo) {
        this.theatreRepo = theatreRepo;
    }

    public List<Theatre> findAll() {
        return theatreRepo.findAll();
    }

    public Page<Theatre> findWithFilters(
            String name,
            String city,
            Pageable pageable
    ) {
        Specification<Theatre> spec = Specification
                .allOf(TheatreSpecs.nameContains(name))
                .and(TheatreSpecs.cityContains(city));

        return theatreRepo.findAll(spec, pageable);
    }

    public Optional<Theatre> findById(String id) {
        return theatreRepo.findById(id);
    }

    public Theatre save(Theatre theatre) {
        return theatreRepo.save(theatre);
    }

    public void deleteById(String id) {
        theatreRepo.deleteById(id);
    }
}
