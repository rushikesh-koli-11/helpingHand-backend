package com.helpingHands.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.SavedFund;

@Repository
public interface SavedFundRepository extends MongoRepository<SavedFund, String> {
}
