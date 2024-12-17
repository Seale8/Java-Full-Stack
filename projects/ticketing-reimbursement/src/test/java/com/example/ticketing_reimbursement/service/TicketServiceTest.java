package com.example.ticketing_reimbursement.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.ticketing_reimbursement.entity.Ticket;
import com.example.ticketing_reimbursement.repository.AccountRepository;
import com.example.ticketing_reimbursement.repository.TicketRepository;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ticket = new Ticket();
        ticket.setAmount(100.0);
        ticket.setDescription("Travel reimbursement");
        ticket.setPostedBy(1); // Assuming this is the ID of an existing account
    }

    /*** Tests for createTicket ***/

    @Test
    public void testCreateTicket_Success() throws Exception {
        // Arrange
        when(accountRepository.existsById(ticket.getPostedBy())).thenReturn(true);
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Act
        Ticket savedTicket = ticketService.createTicket(ticket);

        // Assert
        assertNotNull(savedTicket);
        assertEquals(100.0, savedTicket.getAmount());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void testCreateTicket_AmountIsNull() {
        // Arrange
        ticket.setAmount( null);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> ticketService.createTicket(ticket));
        assertEquals("Amount is empty", exception.getMessage());
    }

    @Test
    public void testCreateTicket_AmountIsZero() {
        // Arrange
        ticket.setAmount(0.0);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> ticketService.createTicket(ticket));
        assertEquals("Amount is empty", exception.getMessage());
    }

    @Test
    public void testCreateTicket_DescriptionIsBlank() {
        // Arrange
        ticket.setDescription("");

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> ticketService.createTicket(ticket));
        assertEquals("Desciption is empty", exception.getMessage());
    }

    @Test
    public void testCreateTicket_AccountDoesNotExist() {
        // Arrange
        when(accountRepository.existsById(ticket.getPostedBy())).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> ticketService.createTicket(ticket));
        assertEquals("Account doesnt exist", exception.getMessage());
    }

    /*** Tests for getAllTickets ***/

    @Test
    public void testGetAllTickets_Success() throws Exception {
        // Arrange
        List<Ticket> ticketList = List.of(ticket);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        // Act
        List<Ticket> result = ticketService.getAllTickets();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    /*** Tests for getTicketById ***/

    @Test
    public void testGetTicketById_Success() throws Exception {
        // Arrange
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        // Act
        Optional<Ticket> result = ticketService.getTicketbyId(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(ticket.getDescription(), result.get().getDescription());
        verify(ticketRepository, times(1)).findById(1);
    }

    @Test
    public void testGetTicketById_NotFound() throws Exception {
        // Arrange
        when(ticketRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Ticket> result = ticketService.getTicketbyId(1);

        // Assert
        assertFalse(result.isPresent());
        verify(ticketRepository, times(1)).findById(1);
    }

    /*** Tests for deleteTicket ***/

    @Test
    public void testDeleteTicket_Success() throws Exception {
        // Act
        ticketService.deleteTicket(1);

        // Assert
        verify(ticketRepository, times(1)).deleteById(1);
    }

    /*** Tests for getAllTicketsFromAccount ***/

    @Test
    public void testGetAllTicketsFromAccount_Success() throws Exception {
        // Arrange
        List<Ticket> tickets = List.of(ticket);
        when(ticketRepository.findTicketByPostedBy(1)).thenReturn(tickets);

        // Act
        List<Ticket> result = ticketService.getAllTicketsFromAccount(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Travel reimbursement", result.get(0).getDescription());
        verify(ticketRepository, times(1)).findTicketByPostedBy(1);
    }
}