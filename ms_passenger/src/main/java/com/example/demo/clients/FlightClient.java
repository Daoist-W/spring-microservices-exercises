package com.example.demo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.Flight;

@FeignClient(name = "flightMS")
public interface FlightClient {

	@GetMapping("/api/flights/{flightId}")
	public Flight getFlights(@PathVariable("flightId") String flightId);
	
	@RequestMapping(value = "/api/flights/{flightId}/{noOfSeats}")
	public void updateFlightSeat(@PathVariable String flightId, @PathVariable int noOfSeats);
}
