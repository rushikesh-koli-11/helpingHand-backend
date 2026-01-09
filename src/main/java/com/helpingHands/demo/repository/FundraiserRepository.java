package com.helpingHands.demo.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.Fundraiser;

@Repository
public interface FundraiserRepository extends MongoRepository<Fundraiser, String> {
    Optional<Fundraiser> findTopByOrderByIdDesc();
}

