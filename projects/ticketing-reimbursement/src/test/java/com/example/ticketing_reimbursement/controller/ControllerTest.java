package com.example.ticketing_reimbursement.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.ticketing_reimbursement.entity.Account;
import com.example.ticketing_reimbursement.entity.Ticket;
import com.example.ticketing_reimbursement.service.AccountService;
import com.example.ticketing_reimbursement.service.TicketService;

public class ControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private Controller controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void registerAccount_ShouldReturnCreatedAccount() throws Exception {
        Account account = new Account();
        account.setUsername("testUser");
        account.setPassword("testPassword");

        when(accountService.registerAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content("{\"username\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void loginAccount_ShouldReturnVerifiedAccount() throws Exception {
        Account account = new Account();
        account.setUsername("testUser");
        account.setPassword("testPassword");

        when(accountService.verifyAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content("{\"username\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void submitTicket_ShouldReturnCreatedTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("Test Ticket");

        when(ticketService.createTicket(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/submit-ticket")
                .contentType("application/json")
                .content("{\"description\":\"Test Ticket\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Ticket"));
    }

    @Test
    void getTicketsByAccountId_ShouldReturnTickets() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("Test Ticket");

        when(ticketService.getAllTicketsFromAccount(1)).thenReturn(List.of(ticket));

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].description").value("Test Ticket"));
    }

    @Test
    void getTicketsByAccountIdAndStatus_ShouldReturnTickets() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("Test Ticket");

        when(ticketService.getTicketsByAccountIdAndStatus(1, "Approved")).thenReturn(List.of(ticket));

        mockMvc.perform(get("/tickets/1/Approved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Test Ticket"));
    }

    @Test
    void deleteTicket_ShouldReturnNoContent() throws Exception {
        doNothing().when(ticketService).deleteTicket(1);

        mockMvc.perform(delete("/tickets/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    void getPendingTickets_ShouldReturnPendingTickets() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription("Pending Ticket");

        when(ticketService.getTicketsByStatus("Pending")).thenReturn(List.of(ticket));

        mockMvc.perform(get("/tickets/pending"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].description").value("Pending Ticket"));
    }

    @Test
    void updateTicketStatus_ShouldReturnOk() throws Exception {
        String json = "{\"status\":\"Approved\"}";

        mockMvc.perform(put("/tickets/1/status")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void getAllRole_ShouldReturnAccounts() throws Exception {
        Account account = new Account();
        account.setUsername("testUser");

        when(accountService.getAllAccountsfromRole("employee")).thenReturn(List.of(account));

        mockMvc.perform(get("/accounts/employee"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].username").value("testUser"));
    }

    @Test
    void promoteEmployee_ShouldReturnPromotedEmployee() throws Exception {
        Account account = new Account();
        account.setUsername("testUser");

        when(accountService.promoteToManager(1)).thenReturn(account);

        mockMvc.perform(put("/promote/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void getAllRole_ShouldReturnNoContentIfNoAccountsFound() throws Exception {
        when(accountService.getAllAccountsfromRole("employee")).thenReturn(new ArrayList<>()); // Empty list

        mockMvc.perform(get("/accounts/employee"))
                .andExpect(status().isNotFound()); // Expecting no content if no accounts found
    }
    
    
}
