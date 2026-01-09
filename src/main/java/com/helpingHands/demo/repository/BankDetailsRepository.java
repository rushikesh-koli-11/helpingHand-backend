package com.helpingHands.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpingHands.demo.entities.BankDetails;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Integer> {

}

