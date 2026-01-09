package com.helpingHands.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.BankDetails;

@Repository
public interface BankDetailsRepository extends MongoRepository<BankDetails, String> {
    Optional<BankDetails> findByFundraiserId(String fundraiserId);
}

