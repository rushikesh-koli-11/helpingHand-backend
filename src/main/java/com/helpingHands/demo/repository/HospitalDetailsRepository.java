package com.helpingHands.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.HospitalDetails;

@Repository
public interface HospitalDetailsRepository extends JpaRepository<HospitalDetails, Integer> {
}

