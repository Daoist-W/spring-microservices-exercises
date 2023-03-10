package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CreditCardDetails;
import com.example.demo.service.CreditCardService;

@RestController
public class PaymentController {
	
	@Autowired
	private CreditCardService cardService;

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateCreditCard(@RequestBody CreditCardDetails creditCard) {
		return new ResponseEntity<>(cardService.validateCreditCard(creditCard), HttpStatus.OK);
	}
}
