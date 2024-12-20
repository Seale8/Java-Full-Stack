package com.example.ticketing_reimbursement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TicketingReimbursementApplicationTests {
	@Autowired
    private ApplicationContext context;

	@Test
	void contextLoads() {
		 assertTrue(context.containsBean("accountService"));
		 assertTrue(context.containsBean("ticketService"));
	}

}
