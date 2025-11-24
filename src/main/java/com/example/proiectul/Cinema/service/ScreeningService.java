package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.model.StaffAssignment;
import com.example.proiectul.Cinema.model.Ticket;
import com.example.proiectul.Cinema.repository.MovieRepository;
import com.example.proiectul.Cinema.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    private final ScreeningRepository repo;
    private final MovieRepository movieRepository;
    private final TicketService ticketService;
    private final StaffAssignmentService staffAssignmentService;


    public ScreeningService(ScreeningRepository repo, MovieRepository movierepo, TicketService ticketService, StaffAssignmentService staffAssignmentService) {
        this.repo = repo;
        this.movieRepository = movierepo;
        this.ticketService = ticketService;
        this.staffAssignmentService = staffAssignmentService;
    }

    public List<Screening> findAll() {
        return repo.findAll();
    }

    public Optional<Screening> findById(String id) {
        return repo.findById(id);
    }

    public List<Screening> findByMovieId(String movieId) {
        return repo.findAll().stream()
                .filter(s -> s.getMovieId().equals(movieId))
                .toList();
    }

    public Screening save(Screening screening) {
        movieRepository.findById(screening.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie does not exist"));
        return repo.save(screening);
    }


    public void deleteById(String id) {
        repo.deleteById(id);
    }

    public Screening findScreeningWithRelations(String id) {
        Screening s = repo.findById(id).orElseThrow();

        List<Ticket> tickets = ticketService.findByScreeningId(id);
        s.setTickets(tickets);

        List<StaffAssignment> staff = staffAssignmentService.findByScreeningId(id);
        s.setAssignments(staff);

        return s;
    }

    public List<Screening> findByHallId(String hallId) {
        return repo.findAll().stream()
                .filter(s -> s.getHallId().equals(hallId))
                .toList();
    }

}
