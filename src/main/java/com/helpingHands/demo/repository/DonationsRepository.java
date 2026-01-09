package com.helpingHands.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.Donations;
@Repository
public interface DonationsRepository extends JpaRepository<Donations, Integer> {
}


