package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Ticket;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class TicketRepository extends InFileRepository<Ticket> {
    private static final Path DATA = Paths.get("src/main/resources/data/ticket.json");
    public TicketRepository() {
        super(DATA, Ticket.class, Ticket::getId, Ticket::setId);
    }
}
