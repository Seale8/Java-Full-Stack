package com.example.ticketing_reimbursement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketing_reimbursement.entity.Account;
import com.example.ticketing_reimbursement.entity.Ticket;
import com.example.ticketing_reimbursement.service.AccountService;
import com.example.ticketing_reimbursement.service.TicketService;

@RestController
public class Controller {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TicketService ticketService;


    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.registerAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering account");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        try{
            Account verifiedAccount = accountService.verifyAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(verifiedAccount);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
     @PostMapping("/submit-ticket")
    public ResponseEntity<Ticket> submitTicket(@RequestBody Ticket ticket) {
        try {
            Ticket createdTicket = ticketService.createTicket(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(createdTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
