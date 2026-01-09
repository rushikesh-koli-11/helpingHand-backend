package com.helpingHands.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
