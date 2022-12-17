/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookingDetails;
import com.example.demo.dto.Flight;
import com.example.demo.dto.PassengerDetails;
import com.example.demo.exception.InfyGoServiceException;
import com.example.demo.service.BookingService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/book")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;

	@PostMapping(value = "/{flightId}/{username}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<BookingDetails> bookFlight(
			@PathVariable("flightId") 
			String flightId,
			@Valid
			@RequestBody 
			PassengerDetails passengerDetails, 
			@PathVariable("username") 
			String username) throws InfyGoServiceException {

		BookingDetails bookingDetails = bookingService.bookFlight(flightId, passengerDetails, username);
		return new ResponseEntity<BookingDetails>(bookingDetails, HttpStatus.OK);

	}

}
