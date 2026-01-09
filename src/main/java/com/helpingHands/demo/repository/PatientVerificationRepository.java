package com.helpingHands.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.PatientVerification;

@Repository
public interface PatientVerificationRepository extends MongoRepository<PatientVerification, String> {
    Optional<PatientVerification> findByFundraiserId(String fundraiserId);
}

