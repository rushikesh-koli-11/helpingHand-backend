package com.helpingHands.demo.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.FundraiserDetails;

@Repository
public interface FundraiserDetailsRepository extends MongoRepository<FundraiserDetails, String> {
    Optional<FundraiserDetails> findByFundraiserId(String fundraiserId);
}

