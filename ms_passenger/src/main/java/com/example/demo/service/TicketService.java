package com.example.demo.service;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.controller.BookingController;
import com.example.demo.dto.Flight;
import com.example.demo.dto.Ticket;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class TicketService {
	
	@Autowired
	private RestTemplate template;
	
	private static final String HTTP_TICKET_MS = "http://TicketMS/api/ticket";
	protected Logger logger = Logger.getLogger(BookingController.class.getName());

	@CircuitBreaker(name = "ticketMSBreaker", fallbackMethod = "prepareTicketFallback")
	public Ticket prepareTicket(String username, Flight flight, double totalFare, int pnr, int noOfSeats) {
		logger.info("prepare Ticket method ");
		Ticket ticket = new Ticket();
		ticket.setPnr(pnr);
		ticket.setBookingDate(new Date());
		System.out.println(ticket.getBookingDate());
		ticket.setDepartureDate(flight.getFlightAvailableDate());
		ticket.setDepartureTime(flight.getDepartureTime());
		ticket.setFlightId(flight.getFlightId());
		ticket.setUserId(username);		
		ticket.setTotalFare(totalFare);
		ticket.setNoOfSeats(noOfSeats);
		template.postForObject(HTTP_TICKET_MS, ticket, Boolean.class);
		return ticket;
	}
	
	public Ticket prepareTicketFallback(String username, Flight flight, double totalFare, int pnr, int noOfSeats, Throwable throwable) {
		logger.info("########################## PREPARE TICKET FALL BACK ##########################");
		return new Ticket();
	}
	
}
