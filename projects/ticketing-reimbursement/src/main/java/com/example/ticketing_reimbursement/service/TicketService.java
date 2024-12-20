package com.example.ticketing_reimbursement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketing_reimbursement.entity.Ticket;
import com.example.ticketing_reimbursement.repository.AccountRepository;
import com.example.ticketing_reimbursement.repository.TicketRepository;


@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Ticket createTicket(Ticket ticket) throws Exception{
        if(ticket.getAmount() == null ||ticket.getAmount() == 0 ){
            throw new Exception("Amount is empty");
        }
        if(ticket.getDescription().isBlank()){
            throw new Exception("Desciption is empty");
        }
        if(!accountRepository.existsById(ticket.getPostedBy())){
            throw new Exception("Account doesnt exist");
        }
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() throws Exception{
      
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketbyId(int id) throws Exception{
      
        return ticketRepository.findById(id);
    }

    public void deleteTicket(int id) throws Exception{
      ticketRepository.deleteById(id);
    }

    public List<Ticket> getAllTicketsFromAccount(int id) throws Exception{
      
        return ticketRepository.findTicketByPostedBy(id);
    }

    public List<Ticket> getTicketsByStatus(String status) throws Exception{
      
        return ticketRepository.findTicketByStatus(status);
    }

    public Ticket updateTicketStatus(int id, String newStatus) throws Exception{
      
            // Fetch the ticket by ID
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);

        if (!optionalTicket.isPresent()) {
            throw new Exception ("Ticket with ID " + id + " does not exist.");
        }

        Ticket ticket = optionalTicket.get();

        // Update the status
        ticket.setStatus(newStatus);

        // Save the updated ticket
       return ticketRepository.save(ticket);
    }

	public List<Ticket> getTicketsByAccountIdAndStatus(Integer accountId, String status) {
        return ticketRepository.findBypostedByAndStatus(accountId, status);
	
	}




    
}
