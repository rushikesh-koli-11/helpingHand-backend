package com.helpingHands.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.PatientVerification;

@Repository
public interface PatientVerificationRepository extends JpaRepository<PatientVerification, Integer> {
}

