package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketService;

@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@PostMapping("/ticket")
	public ResponseEntity<Boolean> createTicket(@RequestBody Ticket ticket){
		ticketService.createTicket(ticket);
		return ResponseEntity.ok(null);
	}
	
}
