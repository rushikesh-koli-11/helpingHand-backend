package com.helpingHands.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.Background;

@Repository
public interface BackgroundRepository extends MongoRepository<Background, String> {
    Optional<Background> findByFundraiserId(String fundraiserId);
}

