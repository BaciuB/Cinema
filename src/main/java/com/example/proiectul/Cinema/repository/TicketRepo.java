package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepo extends RepoMemory<Ticket>{
    public TicketRepo() {
        super(Ticket::getId);
    }
}
