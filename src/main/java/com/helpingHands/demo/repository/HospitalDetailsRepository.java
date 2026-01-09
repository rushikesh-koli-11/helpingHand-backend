package com.helpingHands.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.HospitalDetails;

@Repository
public interface HospitalDetailsRepository extends MongoRepository<HospitalDetails, String> {
    Optional<HospitalDetails> findByFundraiserId(String fundraiserId);
}

