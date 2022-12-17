package com.example.demo.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.controller.BookingController;
import com.example.demo.dto.Flight;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class FlightService {

	@Autowired
	private RestTemplate template;
	
	private static final String HTTP_FLIGHT_MS = "http://FlightMS/api/flights/";
	protected Logger logger = Logger.getLogger(BookingController.class.getName());

	@CircuitBreaker(name = "flightMSBreaker", fallbackMethod = "getFlightFallback")
	public Flight getFlight(String flightId) {
		logger.info("get Flight method ");
		Flight flight = template.getForObject(HTTP_FLIGHT_MS + flightId, Flight.class);
		return flight;
	}
	
	@CircuitBreaker(name = "flightMSBreaker", fallbackMethod = "updateFlightFallback")
	public void updateFlightSeat(String flightId, int noOfSeats) {
		logger.info("update Flight method ");
		template.getForEntity(HTTP_FLIGHT_MS + flightId + "/" + noOfSeats, null);
	}
	
	public Flight getFlightFallback(String flightId, Throwable throwable) {
		logger.info("########################## GET FLIGHT FALL BACK ##########################");
		return new Flight();
	}
	
	public void updateFlightFallback(String flightId, int noOfSeats, Throwable throwable) {
		logger.info("########################## GET FLIGHT FALL BACK ##########################");
	}
	
	

}
