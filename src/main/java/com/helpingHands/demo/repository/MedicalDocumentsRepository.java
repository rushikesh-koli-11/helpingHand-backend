package com.helpingHands.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpingHands.demo.entities.MedicalDocuments;

public interface MedicalDocumentsRepository extends JpaRepository<MedicalDocuments, Integer> {
    
    // Custom query method to fetch MedicalDocuments by fundraiserId
    Optional<MedicalDocuments> findByFundraiser_Id(int fundraiserId);
}


