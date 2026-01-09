package com.helpingHands.demo.repository;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpingHands.demo.entities.FundraiserDetails;

@Repository
public interface FundraiserDetailsRepository extends JpaRepository<FundraiserDetails, Integer> {
	@Query("SELECT f FROM FundraiserDetails WHERE f.id =: fundraiserId")
	Optional<FundraiserDetails> findByFundraiserId(Integer fundraiserId);
}

