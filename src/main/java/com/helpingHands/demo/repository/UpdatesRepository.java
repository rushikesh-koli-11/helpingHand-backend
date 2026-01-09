package com.helpingHands.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.Updates;

@Repository
public interface UpdatesRepository extends MongoRepository<Updates, String> {
    List<Updates> findByFundraiserId(String fundraiserId);
}
