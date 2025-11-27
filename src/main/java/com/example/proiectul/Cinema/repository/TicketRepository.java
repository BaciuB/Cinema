package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}
