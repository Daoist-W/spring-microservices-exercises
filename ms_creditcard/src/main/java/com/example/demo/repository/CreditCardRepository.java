package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CreditCardDetails;

public interface CreditCardRepository extends JpaRepository<CreditCardDetails, String> {
 
}
