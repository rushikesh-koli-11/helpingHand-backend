package com.helpingHands.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.MedicalDocuments;

@Repository
public interface MedicalDocumentsRepository extends MongoRepository<MedicalDocuments, String> {
    Optional<MedicalDocuments> findByFundraiserId(String fundraiserId);
}


