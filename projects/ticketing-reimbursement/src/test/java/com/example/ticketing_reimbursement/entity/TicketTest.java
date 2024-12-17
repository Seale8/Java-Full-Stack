package com.example.ticketing_reimbursement.entity;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketTest {

    @Test
    void testTicketConstructorAndGettersSetters() {
        Ticket ticket = new Ticket(1, "Description", 100);

        // Verify getters and setters
        assertEquals(1, ticket.getPostedBy());
        assertEquals("Description", ticket.getDescription());
        assertEquals(100, ticket.getAmount());
        assertEquals("Pending", ticket.getStatus()); // Default status
    }

    @Test
    void testTicketConstructorWithAllFields() {
        Ticket ticket = new Ticket(1, 22,"Description", 100, "Approved");

        // Verify all fields
        assertEquals(1, ticket.getTicketId());
        assertEquals(22, ticket.getPostedBy());
        assertEquals("Description", ticket.getDescription());
        assertEquals(100, ticket.getAmount());
        assertEquals("Approved", ticket.getStatus());
    }
}
