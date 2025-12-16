// src/main/java/com/example/proiectul/Cinema/service/TicketService.java
package com.example.proiectul.Cinema.service;

import com.example.proiectul.Cinema.model.Ticket;
import com.example.proiectul.Cinema.repository.TicketRepository;
import com.example.proiectul.Cinema.repository.spec.TicketSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepo;

    public TicketService(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> findAll() {
        return ticketRepo.findAll();
    }

    public Optional<Ticket> findById(String id) {
        return ticketRepo.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public void deleteById(String id) {
        ticketRepo.deleteById(id);
    }

    public List<Ticket> findByScreeningId(String screeningId) {
        return ticketRepo.findByScreening_Id(screeningId);
    }

    public List<Ticket> findByCustomerId(String customerId) {
        return ticketRepo.findByCustomer_Id(customerId);
    }

    public boolean seatAlreadyBookedForScreening(String screeningId, String seatId) {
        return ticketRepo.existsByScreening_IdAndSeat_Id(screeningId, seatId);
    }

    public Page<Ticket> findWithFilters(String screeningId,
                                        String seatId,
                                        String customerName,
                                        Double minPrice,
                                        Double maxPrice,
                                        Pageable pageable) {

        Specification<Ticket> spec = Specification
                .allOf(TicketSpecs.screeningIdEquals(screeningId))
                .and(TicketSpecs.seatIdEquals(seatId))
                .and(TicketSpecs.customerNameContains(customerName))
                .and(TicketSpecs.priceGte(minPrice))
                .and(TicketSpecs.priceLte(maxPrice));

        return ticketRepo.findAll(spec, pageable);
    }
}
