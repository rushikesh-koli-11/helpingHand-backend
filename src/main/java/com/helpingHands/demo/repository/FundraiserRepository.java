package com.helpingHands.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.Fundraiser;

@Repository
public interface FundraiserRepository extends JpaRepository<Fundraiser, Integer> {
	 Optional<Fundraiser> findTopByOrderByIdDesc();
}

