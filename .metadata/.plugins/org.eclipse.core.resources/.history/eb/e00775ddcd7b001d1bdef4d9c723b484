package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CreditCardDetails;
import com.example.demo.repository.CreditCardRepository;

@Service
public class CreditCardService {
	
	@Autowired
	private CreditCardRepository cardRepository;
	
	public boolean validateCreditCard(CreditCardDetails cardDetails) {
		Optional<CreditCardDetails> optional = cardRepository.findById(cardDetails.getCardNumber());
		CreditCardDetails card = optional.orElse(null);
		if(card == null) return false;
		if(
				card.getApin().equals(cardDetails.getApin()) &&
				card.getCardHolderName().equals(cardDetails.getCardHolderName()) &&
				card.getCvv().equals(cardDetails.getCvv())
				) {
			return true;
		}
		return false;
	}
}
