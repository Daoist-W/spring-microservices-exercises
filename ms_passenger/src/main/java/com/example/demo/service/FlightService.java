package com.example.demo.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.clients.FlightClient;
import com.example.demo.controller.BookingController;
import com.example.demo.dto.Flight;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class FlightService {

	@Autowired
	private FlightClient client;
	
	protected Logger logger = Logger.getLogger(BookingController.class.getName());

	@CircuitBreaker(name = "flightMSBreaker", fallbackMethod = "getFlightFallback")
	public Flight getFlight(String flightId) {
		logger.info("get Flight method ");
		Flight flight = client.getFlights(flightId);
		return flight;
	}
	
	@CircuitBreaker(name = "flightMSBreaker", fallbackMethod = "updateFlightFallback")
	public void updateFlightSeat(String flightId, int noOfSeats) {
		logger.info("update Flight method ");
		client.updateFlightSeat(flightId, noOfSeats);
	}
	
	public Flight getFlightFallback(String flightId, Throwable throwable) {
		logger.info("########################## GET FLIGHT FALL BACK ##########################");
		return new Flight();
	}
	
	public void updateFlightFallback(String flightId, int noOfSeats, Throwable throwable) {
		logger.info("########################## GET FLIGHT FALL BACK ##########################");
	}
	
	

}
