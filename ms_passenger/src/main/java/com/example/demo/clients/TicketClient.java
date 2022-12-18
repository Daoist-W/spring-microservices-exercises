package com.example.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.Ticket;

@FeignClient("ticketMS")
public interface TicketClient {

	@PostMapping("api/ticket")
	public void createTicket(@RequestBody Ticket ticket);
	
}

