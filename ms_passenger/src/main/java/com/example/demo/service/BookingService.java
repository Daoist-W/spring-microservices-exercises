package com.example.demo.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.controller.BookingController;
import com.example.demo.dto.BookingDetails;
import com.example.demo.dto.Flight;
import com.example.demo.dto.PassengerDetails;
import com.example.demo.dto.Ticket;
import com.example.demo.entity.Passenger;
import com.example.demo.exception.ExceptionConstants;
import com.example.demo.exception.InfyGoServiceException;

@Service
public class BookingService {

	protected Logger logger = Logger.getLogger(BookingController.class.getName());

	@Autowired
	private PassengerService passengerService;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private FlightService flightService;
	
	private int noOfSeats;
	
	public BookingDetails bookFlight(String flightId, PassengerDetails passengerDetails, String username) throws InfyGoServiceException {
		logger.log(Level.INFO, "Book Flight method ");
		
		List<Passenger> passengerList = passengerDetails.getPassengerList();
		if(passengerList.isEmpty())
        	throw new InfyGoServiceException(ExceptionConstants.PASSENGER_LIST_EMPTY.toString());

		logger.log(Level.INFO, passengerDetails.toString());
		int pnr = (int) (Math.random() * 1858955);

		Flight flight = flightService.getFlight(flightId);

		double fare = flight.getFare();
		System.out.println("Fare per person:****** " + fare);
		System.out.println("List size:****** " + passengerList.size());
		double totalFare = fare * (passengerList.size());

		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setPassengerList(passengerList);
		bookingDetails.setPnr(pnr);
		bookingDetails.setTotalFare(totalFare);
		
		Ticket ticket = ticketService.prepareTicket(username, flight, totalFare, pnr, noOfSeats);
		
		assignPassengers(bookingDetails.getPassengerList(), ticket);
		
		flightService.updateFlightSeat(flightId, noOfSeats);
		
		return bookingDetails;
	}

	
	private void assignPassengers(List<Passenger> passengers, Ticket ticket) {
		for (Passenger passenger : passengers) {
			passenger.setTicket(ticket);	    
		}
		passengerService.createPassenger(passengers);
	}
	
	
}
