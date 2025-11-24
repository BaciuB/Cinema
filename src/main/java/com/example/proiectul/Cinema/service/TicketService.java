package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Ticket;
import com.example.proiectul.Cinema.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepo;

    public TicketService(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> findAll() { return ticketRepo.findAll(); }
    public Optional<Ticket> findById(String id) { return ticketRepo.findById(id); }
    public Ticket save(Ticket ticket) { return ticketRepo.save(ticket); }
    public void deleteById(String id) { ticketRepo.deleteById(id); }

    public List<Ticket> findByCustomerId(String customerId) {
        return ticketRepo.findAll().stream()
                .filter(t -> t.getCustomerId().equals(customerId))
                .toList();



    }

    public List<Ticket> findByScreeningId(String screeningId) {
        return ticketRepo.findAll().stream()
                .filter(t -> t.getScreeningId().equals(screeningId))
                .toList();
    }
}
