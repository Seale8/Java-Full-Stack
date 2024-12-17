package com.example.ticketing_reimbursement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ticketing_reimbursement.entity.Ticket;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket ticket;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        ticket = new Ticket(1, "Test Description", 200);
        ticketRepository.save(ticket);
    }

    @Test
    void testFindById() {
        Optional<Ticket> foundTicket = ticketRepository.findById(ticket.getTicketId());
        assertTrue(foundTicket.isPresent());
        assertEquals("Test Description", foundTicket.get().getDescription());
    }

    @Test
    void testSaveTicket() {
        Ticket newTicket = new Ticket(2, "New Ticket Description", 300);
        Ticket savedTicket = ticketRepository.save(newTicket);

        assertEquals(2, savedTicket.getPostedBy());
        assertEquals("New Ticket Description", savedTicket.getDescription());
    }

    @Test
    void testDeleteTicket() {
        ticketRepository.delete(ticket);
        Optional<Ticket> deletedTicket = ticketRepository.findById(ticket.getTicketId());
        assertTrue(deletedTicket.isEmpty());
    }
    
}
