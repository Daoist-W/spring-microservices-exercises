/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.BookingDetails;
import com.example.demo.dto.Flight;
import com.example.demo.dto.PassengerDetails;
import com.example.demo.dto.Ticket;
import com.example.demo.entity.Passenger;
import com.example.demo.exception.ARSServiceException;
import com.example.demo.exception.ExceptionConstants;
import com.example.demo.exception.InfyGoServiceException;
import com.example.demo.service.PassengerService;
import com.example.demo.utility.ClientErrorInformation;


@RestController

@RequestMapping("/book")
public class BookingController {

	private static final String HTTP_LOCALHOST_9100_API_TICKET = "http://localhost:9100/api/ticket";

	private static final String HTTP_LOCALHOST_9200_API_FLIGHTS = "http://localhost:9200/api/flights/";

	protected Logger logger = Logger.getLogger(BookingController.class.getName());

	@Autowired
	private PassengerService passengerService;
	private Ticket ticket;
	private int noOfSeats;
	
	@Autowired
	private DiscoveryClient client;
	
	private RestTemplate template = new RestTemplate();
	

	public BookingController() {
		ticket = new Ticket();		
	}
	
	private String getServiceUri(String service, Integer index) {
		List<ServiceInstance> serviceURis = client.getInstances(service);
		if(serviceURis.isEmpty()) return null;
		return serviceURis.get(index).getUri().toString();
	}


	@PostMapping(value = "/{flightId}/{username}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<BookingDetails> bookFlight(@PathVariable("flightId") String flightId,
		 @Valid @RequestBody PassengerDetails passengerDetails, @PathVariable("username") String username,Errors errors) throws InfyGoServiceException, ARSServiceException {
			
		    if (errors.hasErrors()) {
			return new ResponseEntity(new ClientErrorInformation(HttpStatus.BAD_REQUEST.value(),errors.getFieldError("passengerList").getDefaultMessage()), HttpStatus.BAD_REQUEST);
		    }
		if(passengerDetails.getPassengerList().isEmpty())
        	throw new InfyGoServiceException(ExceptionConstants.PASSENGER_LIST_EMPTY.toString());
        	
		List<Passenger> passengerList = new ArrayList<Passenger>();
		for (Passenger passengers : passengerDetails.getPassengerList()) {
			passengerList.add(passengers);
		    

		}
		System.out.println(passengerList.toString());

		logger.log(Level.INFO, "Book Flight method ");

		logger.log(Level.INFO, passengerDetails.toString());
		int pnr = (int) (Math.random() * 1858955);

		ticket.setPnr(pnr);
//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
          
//		Flight flight = flightService.getFlights(flightId);
//		Flight flight = template.getForObject(HTTP_LOCALHOST_9200_API_FLIGHTS + flightId, Flight.class);
		String flightBaseURI = getServiceUri("FlightMS", 0) + "/api"; 
		System.out.println("flight uri: " + flightBaseURI);
		Flight flight = template.getForObject(flightBaseURI + "/flights/" + flightId, Flight.class);

		double fare = flight.getFare();
		System.out.println("Fare per person:****** " + fare);
		System.out.println("List size:****** " + passengerDetails.getPassengerList().size());
		double totalFare = fare * (passengerDetails.getPassengerList().size());

		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setPassengerList(passengerDetails.getPassengerList());
		bookingDetails.setPnr(pnr);
		bookingDetails.setTotalFare(totalFare);
		ticket.setBookingDate(new Date());
		System.out.println(ticket.getBookingDate());
		ticket.setDepartureDate(flight.getFlightAvailableDate());
		ticket.setDepartureTime(flight.getDepartureTime());
		ticket.setFlightId(flight.getFlightId());
		ticket.setUserId(username);		
		ticket.setTotalFare(totalFare);
		noOfSeats = passengerDetails.getPassengerList().size();
		ticket.setNoOfSeats(noOfSeats);
//	    ticketService.createTicket(ticket);
//		template.postForObject(HTTP_LOCALHOST_9100_API_TICKET, ticket, Boolean.class);
		String ticketBaseURI = getServiceUri("TicketMS", 0) + "/api"; 
		System.out.println("ticket uri: " + ticketBaseURI);
		template.postForObject(ticketBaseURI + "/ticket", ticket, Boolean.class);
		
		addPassengers(bookingDetails.getPassengerList());
		
//		flightService.updateFlight(flightId, noOfSeats);
		template.getForEntity(flightBaseURI + "/flights/" + flightId + "/" + noOfSeats, null);

		return new ResponseEntity<BookingDetails>(bookingDetails, HttpStatus.OK);

	}

	private void addPassengers(List<Passenger> passengers) {
		
		for (Passenger passenger : passengers) {
			passenger.setTicket(ticket);	    

		}

		passengerService.createPassenger(passengers);

	}

}
