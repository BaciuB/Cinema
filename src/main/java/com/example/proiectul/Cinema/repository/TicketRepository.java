package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    List<Ticket> findByScreening_Id(String screeningId);

    List<Ticket> findByCustomer_Id(String customerId);

    boolean existsByScreening_IdAndSeat_Id(String screeningId, String seatId);
}
