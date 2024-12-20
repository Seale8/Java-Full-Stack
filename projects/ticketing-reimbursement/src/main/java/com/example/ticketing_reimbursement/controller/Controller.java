package com.example.ticketing_reimbursement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketing_reimbursement.entity.Account;
import com.example.ticketing_reimbursement.entity.Ticket;
import com.example.ticketing_reimbursement.service.AccountService;
import com.example.ticketing_reimbursement.service.TicketService;

@RestController
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private TicketService ticketService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        logger.info("Attempting to register account: {}", account);
        try {
            Account createdAccount = accountService.registerAccount(account);
            logger.info("Account registered successfully with ID: {}", createdAccount.getAccountId());

            return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
        } catch (Exception e) {
            logger.error("Error registering account: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody Account account) {
        logger.info("Login attempt for account: {}", account.getUsername());
        try {
            Account verifiedAccount = accountService.verifyAccount(account);
            logger.info("Login successful for account ID: {}", verifiedAccount.getAccountId());
            return ResponseEntity.status(HttpStatus.OK).body(verifiedAccount);
        } catch (Exception e) {
            logger.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/submit-ticket")
    public ResponseEntity<?> submitTicket(@RequestBody Ticket ticket) {
        try {
            Ticket createdTicket = ticketService.createTicket(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(createdTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/tickets/{accountId}")
    public ResponseEntity<?> getTicketsByAccountId(@PathVariable Integer accountId) {
        logger.info("Fetching tickets for account ID: {}", accountId);

        try {
            List<Ticket> tickets = ticketService.getAllTicketsFromAccount(accountId);
            logger.info("Found {} tickets for account ID: {}", tickets.size(), accountId);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(tickets);
        } catch (Exception e) {
            logger.error("Error fetching tickets for account ID {}: {}", accountId, e.getMessage());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }

    }

    @GetMapping("/tickets/{accountId}/{status}")
    public ResponseEntity<?> getTicketsByAccountIdandStatus(@PathVariable Integer accountId,
            @PathVariable String status) {
        try {
            // Get tickets filtered by accountId and status
            List<Ticket> tickets = ticketService.getTicketsByAccountIdAndStatus(accountId, status);

            if (tickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(tickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/tickets/{accountId}/history")
    public ResponseEntity<?> getEmployeeOldTickets(@PathVariable Integer accountId) {
        try {
            // Get tickets filtered by accountId and status
            List<Ticket> ticketsa = ticketService.getTicketsByAccountIdAndStatus(accountId, "Approved");
            List<Ticket> ticketsb = ticketService.getTicketsByAccountIdAndStatus(accountId, "Denied");
            List<Ticket> combinedTickets = new ArrayList<>(ticketsa); // Create a new list with elements from ticketsa
            combinedTickets.addAll(ticketsb);

            if (combinedTickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("None of your tickets have been proceesed");
            }

            return ResponseEntity.status(HttpStatus.OK).body(combinedTickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/tickets/history")
    public ResponseEntity<?> getallOldTickets() {
        try {
            // Get tickets filtered by accountId and status
            List<Ticket> ticketsa = ticketService.getTicketsByStatus("Approved");
            List<Ticket> ticketsb = ticketService.getTicketsByStatus("Denied");
            List<Ticket> combinedTickets = new ArrayList<>(ticketsa); // Create a new list with elements from ticketsa
            combinedTickets.addAll(ticketsb);

            if (combinedTickets.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No tickets have been processed");
            }

            return ResponseEntity.status(HttpStatus.OK).body(combinedTickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("tickets/{ticketId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer ticketId) throws Exception {
        logger.info("Attempting to delete ticket with ID: {}", ticketId);

        try {

            ticketService.deleteTicket(ticketId);
            logger.info("Ticket with ID {} deleted successfully", ticketId);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(1);
        } catch (Exception e) {
            logger.error("Error deleting ticket ID {}: {}", ticketId, e.getMessage());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping("/tickets/pending")
    public ResponseEntity<?> getPendingTickets() {
        try {
            List<Ticket> pendingTickets = ticketService.getTicketsByStatus("Pending");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(pendingTickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }

    }

    @PutMapping("/tickets/{id}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable int id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        try {
            ticketService.updateTicketStatus(id, newStatus);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());

        }

    }

    @GetMapping("/accounts/{role}")
    public ResponseEntity<?> getAllRole(@PathVariable String role) {
        try {
            List<Account> employees = accountService.getAllAccountsfromRole(role);
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found");  // No body
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }

    }

    @PutMapping("/promote/{accountId}")
    public ResponseEntity<?> promoteEmployee(@PathVariable Integer accountId) {
        try {
            Account promotedEmployee = accountService.promoteToManager(accountId);
            return ResponseEntity.status(HttpStatus.OK).body(promotedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
