package com.example.ticketing_reimbursement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticketing_reimbursement.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
    List<Ticket> findTicketByPostedBy(int accountId);

    List<Ticket> findTicketByStatus(String status);

    List<Ticket> findBypostedByAndStatus(Integer accountId, String status);

}
